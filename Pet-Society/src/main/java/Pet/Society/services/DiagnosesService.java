package Pet.Society.services;

import Pet.Society.models.dto.diagnoses.DiagnosesDTO;
import Pet.Society.models.dto.diagnoses.DiagnosesDTOResponse;
import Pet.Society.models.entities.AppointmentEntity;
import Pet.Society.models.entities.DiagnosesEntity;
import Pet.Society.models.entities.DoctorEntity;
import Pet.Society.models.entities.PetEntity;
import Pet.Society.models.enums.Status;
import Pet.Society.models.exceptions.AppointmentNotFoundException;
import Pet.Society.models.exceptions.DiagnosesNotFoundException;
import Pet.Society.repositories.AppointmentRepository;
import Pet.Society.repositories.DiagnosesRepository;
import Pet.Society.repositories.DoctorRepository;
import Pet.Society.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class DiagnosesService {

    private final DiagnosesRepository diagnosesRepository;
    private final PetRepository petRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;

    @Autowired
    public DiagnosesService(DiagnosesRepository diagnosesRepository,
                            PetRepository petRepository,
                            DoctorRepository doctorRepository,
                            AppointmentRepository appointmentRepository,
                            AppointmentService appointmentService) {
        this.diagnosesRepository = diagnosesRepository;
        this.petRepository = petRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
    }

    public DiagnosesDTO save(DiagnosesDTO dto) {

        AppointmentEntity appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        if (dto.getDate().isAfter(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("the date entered is incorrect");
        }

        DiagnosesEntity diagnosis = DiagnosesEntity.builder()
                .diagnose(dto.getDiagnose())
                .treatment(dto.getTreatment())
                .doctor(appointment.getDoctor())
                .pet(appointment.getPet())
                .appointment(appointment)
                .date(dto.getDate())
                .build();

        appointment.setStatus(Status.SUCCESSFULLY);
        appointment.setDiagnoses(diagnosis);
        this.diagnosesRepository.save(diagnosis);
        return dto;
    }

    public DiagnosesEntity findById(Long id) {
        return diagnosesRepository.findById(id)
                .orElseThrow(() -> new DiagnosesNotFoundException("Diagnosis " + id + " not found"));
    }


    public DiagnosesDTOResponse findLastById(long id) {
        if (diagnosesRepository.findLastById(id).isPresent()) {
            DiagnosesEntity diagnosis = diagnosesRepository.findLastById(id).get();
            return new DiagnosesDTOResponse(diagnosis.getDiagnose(),
                    diagnosis.getTreatment(),
                    diagnosis.getDoctor().getId(),
                    diagnosis.getPet().getId(),
                    diagnosis.getAppointment().getId(),
                    diagnosis.getDate());
        } else {
            throw new DiagnosesNotFoundException("Diagnosis " + id + " not found");
        }
    }

    public Page<DiagnosesDTOResponse> findByPetId(long id, Pageable pageable) {
        if (diagnosesRepository.findByPetId(id, pageable).isEmpty()) {
            throw new DiagnosesNotFoundException("Diagnoses of Pet id : " + id + " not found");
        }
        return diagnosesRepository.findByPetId(id, pageable).map(diagnosesEntity -> new DiagnosesDTOResponse(diagnosesEntity.getDiagnose(),
                diagnosesEntity.getTreatment(),
                diagnosesEntity.getDoctor().getId(),
                diagnosesEntity.getPet().getId(),
                diagnosesEntity.getAppointment().getId(),
                diagnosesEntity.getDate()));

    }

    public Page<DiagnosesDTOResponse> findAll(Pageable pageable) {
        return diagnosesRepository.findAll(pageable).map(diagnoses -> new DiagnosesDTOResponse(diagnoses.getDiagnose(),
                diagnoses.getTreatment(),
                diagnoses.getDoctor().getId(),
                diagnoses.getPet().getId(),
                diagnoses.getAppointment().getId(),
                diagnoses.getDate()));
    }

    public Page<DiagnosesDTOResponse> findByDoctorId(long id, Pageable pageable) {

        if (diagnosesRepository.findByDoctorId(id, pageable).isEmpty()) {
            throw new DiagnosesNotFoundException("Diagnoses of Doctor id : " + id + " not found");
        }
        return diagnosesRepository.findByDoctorId(id, pageable).map(diagnoses -> new DiagnosesDTOResponse(
                diagnoses.getDiagnose(),
                diagnoses.getTreatment(),
                diagnoses.getDoctor().getId(),
                diagnoses.getPet().getId(),
                diagnoses.getAppointment().getId(),
                diagnoses.getDate()
        ));
    }
}
