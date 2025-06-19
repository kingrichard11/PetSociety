package Pet.Society.services;

import Pet.Society.models.dto.appointment.AppointmentDTO;
import Pet.Society.models.dto.appointment.AppointmentResponseDTO;
import Pet.Society.models.dto.appointment.AppointmentUpdateDTO;
import Pet.Society.models.dto.client.ClientDTO;
import Pet.Society.models.dto.pet.AssingmentPetDTO;
import Pet.Society.models.dto.pet.PetDTO;
import Pet.Society.models.entities.AppointmentEntity;
import Pet.Society.models.entities.ClientEntity;
import Pet.Society.models.entities.DoctorEntity;
import Pet.Society.models.entities.PetEntity;
import Pet.Society.models.enums.Reason;
import Pet.Society.models.enums.Status;
import Pet.Society.models.exceptions.AppointmentDoesntExistException;
import Pet.Society.models.exceptions.DuplicatedAppointmentException;
import Pet.Society.models.exceptions.UnavailableAppointmentException;
import Pet.Society.models.interfaces.Mapper;
import Pet.Society.repositories.AppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements Mapper<AppointmentDTO,AppointmentEntity> {

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PetService petService;
    private final ClientService clientService;


    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, DoctorService doctorService, PetService petService, ClientService clientService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.petService = petService;
        this.clientService = clientService;
    }


    //MAYBE IS THE CORRECT WAY.
    //Si ya existe la cita; Excepcion
    //Si existe una cita que se solape con otra; Excepción

    @Transactional(rollbackOn = DuplicatedAppointmentException.class)
    public AppointmentDTO save (AppointmentDTO appointmentDTO) {

        DoctorEntity findDoctor = this.doctorService.findById1(appointmentDTO.getDoctor());

        AppointmentEntity appointment = AppointmentEntity.builder()
                                        .startDate(appointmentDTO.getStartTime())
                                        .endDate(appointmentDTO.getEndTime())
                                        .reason(appointmentDTO.getReason())
                                        .status(Status.AVAILABLE)
                                        .doctor(findDoctor)
                                        .approved(true)
                                        .build();
        if (isOverlapping(appointment)) {
            throw new DuplicatedAppointmentException("The appointment already exists; it has the same hour.");
        }

        return toDTO(this.appointmentRepository.save(appointment));
    }
    @Transactional
    public AppointmentResponseDTO bookAppointment(Long idAppointment, AssingmentPetDTO dto) {
        AppointmentEntity findAppointment = this.appointmentRepository.
                findById(idAppointment).orElseThrow(() -> new AppointmentDoesntExistException("Appointment not found"));

        if (findAppointment.getPet() != null) {
            throw new UnavailableAppointmentException("This appointment is already booked");
        }
        if(!findAppointment.isApproved()) {
            throw new UnavailableAppointmentException("The client has an unpaid appointment");
        }

        PetEntity findPet = this.petService.findById(dto.getPetId());

        findAppointment.setPet(findPet);
        this.appointmentRepository.save(findAppointment);

        return AppointmentResponseDTO.builder()
                .startTime(findAppointment.getStartDate())
                .startTime(findAppointment.getEndDate())
                .reason(findAppointment.getReason())
                .doctorName(findAppointment.getDoctor().getName())
                .petName(findPet.getName())
                .aproved(findAppointment.isApproved())
                .build();
    }

    //Confirm if an Appointment doesn't overlap with another Appointment
   /// return if exist any match with another appointment in our database.
    private boolean isOverlapping(AppointmentEntity newAppointment) {

        return appointmentRepository.findAppointmentByDoctor(newAppointment.getDoctor())
                .stream()
                .anyMatch(existing ->
                        newAppointment.getStartDate().isBefore(existing.getEndDate()) &&
                                newAppointment.getEndDate().isAfter(existing.getStartDate())
                );
    }

    ///WORKS
    public AppointmentDTO updateAppointment(AppointmentUpdateDTO appointmentUpdateDTO, long id) {
        Optional<AppointmentEntity> existingAppointment = this.appointmentRepository.findById(id);
        if (existingAppointment.isEmpty()) {
            throw new AppointmentDoesntExistException("Appointment does not exist");
        }
        AppointmentEntity appointmentToUpdate = existingAppointment.get();
        if (appointmentUpdateDTO.getApproved() != null) {
            appointmentToUpdate.setApproved(appointmentUpdateDTO.getApproved());
        }

        if (appointmentUpdateDTO.getReason() != null) {
            appointmentToUpdate.setReason(appointmentUpdateDTO.getReason());
        }

        if (appointmentUpdateDTO.getStatus() != null) {
            appointmentToUpdate.setStatus(appointmentUpdateDTO.getStatus());
        }

        if (appointmentUpdateDTO.getPetId() != null) {
            PetEntity pet = this.petService.findById(appointmentUpdateDTO.getPetId());
            appointmentToUpdate.setPet(pet);
        }

        if (appointmentUpdateDTO.getDiagnosesId() != null) {
            // Aquí deberías buscar el Diagnóstico por ID y asignarlo a la cita
            // DiagnosesEntity diagnoses = this.diagnosesService.getDiagnosesById(appointmentUpdateDTO.getDiagnosesId());
            // appointmentToUpdate.setDiagnoses(diagnoses);
        }
        this.appointmentRepository.save(appointmentToUpdate);
        return toDTO(appointmentToUpdate);
    }

    public void cancelAppointment(long id) {
        Optional<AppointmentEntity> existingAppointment = this.appointmentRepository.findById(id);
        if (existingAppointment.isEmpty()) {
            throw new AppointmentDoesntExistException("Appointment does not exist");
        }
        AppointmentUpdateDTO appointmentUpdateDTO = new AppointmentUpdateDTO();
        appointmentUpdateDTO.setStatus(Status.CANCELED);
        updateAppointment(appointmentUpdateDTO, id);
    }

    public List<AppointmentResponseDTO> getLastAppointmentsByClientId(long id) {
        Optional<ClientDTO> client = Optional.ofNullable(this.clientService.findById(id));
        if (client.isEmpty()) {
            throw new AppointmentDoesntExistException("Client does not exist");
        }
        return  this.appointmentRepository.findAllByPetClientId(id).stream()
                .filter(appointment -> appointment.getStatus().equals(Status.SUCCESSFULLY)).
                map(appointmentEntity -> AppointmentResponseDTO.builder()
                        .startTime(appointmentEntity.getStartDate())
                        .endTime(appointmentEntity.getEndDate())
                        .reason(appointmentEntity.getReason())
                        .aproved(appointmentEntity.isApproved())
                        .petName(appointmentEntity.getPet().getName())
                        .doctorName(appointmentEntity.getDoctor().getName())
                        .build()).collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAllAppointmentsByPetId(long id) {
      Optional <PetEntity> pet = Optional.ofNullable(this.petService.findById(id));
        if(pet.isEmpty()){
            throw new AppointmentDoesntExistException("Pet does not exist");
        }
        return this.appointmentRepository.findAllByPetId(id).stream()
                .filter(appointment -> appointment.getStatus().equals(Status.SUCCESSFULLY)).
                map(appointmentEntity -> AppointmentResponseDTO.builder()
                        .startTime(appointmentEntity.getStartDate())
                        .endTime(appointmentEntity.getEndDate())
                        .reason(appointmentEntity.getReason())
                        .aproved(appointmentEntity.isApproved())
                        .petName(appointmentEntity.getPet().getName())
                        .doctorName(appointmentEntity.getDoctor().getName())
                        .build()).collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAvailableAppointmentsDoctorForToday(long id) {
        if(!doctorService.doctorExistById(id)){
            throw new AppointmentDoesntExistException("Doctor does not exist");
        }
        return this.appointmentRepository.findAllByDoctorId(id).stream() .filter(appointment -> appointment.getStartDate().isAfter(LocalDateTime.now())).
                map(appointmentEntity -> AppointmentResponseDTO.builder()
                        .startTime(appointmentEntity.getStartDate())
                        .endTime(appointmentEntity.getEndDate())
                        .reason(appointmentEntity.getReason())
                        .aproved(appointmentEntity.isApproved())
                        .petName(appointmentEntity.getPet().getName())
                        .doctorName(appointmentEntity.getDoctor().getName())
                        .build()).collect(Collectors.toList());
    }

    public boolean petHasAppointment(long id) {
        List<AppointmentEntity> appointments = this.appointmentRepository.findAllByPetId(id);
        if(!appointments.isEmpty()){
           return true;
        }
       return false;
    }


    @Override
    public AppointmentEntity toEntity(AppointmentDTO dto) {
        return AppointmentEntity.builder()
                .startDate(dto.getStartTime())
                .endDate(dto.getEndTime())
                .reason(dto.getReason())
                .doctor(this.doctorService.findById1(dto.getDoctor()))
                .approved(dto.isAproved())
                .build();
    }

    @Override
    public AppointmentDTO toDTO(AppointmentEntity entity) {
        return AppointmentDTO.builder()
                .startTime(entity.getStartDate())
                .endTime(entity.getEndDate())
                .reason(entity.getReason())
                .doctor(entity.getDoctor().getId())
                .aproved(entity.isApproved())
                .build();
    }
}
