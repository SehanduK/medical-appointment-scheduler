itapassepackage com.medschedule.service;

import com.medschedule.dto.AppointmentRequestDTO;
import com.medschedule.dto.AppointmentResponseDTO;
import com.medschedule.model.AppointmentStatus;

import java.util.List;
import java.util.Map;

/**
 * OOP CONCEPT: ABSTRACTION + POLYMORPHISM
 *
 * - ABSTRACTION  : Interface defines WHAT to do, not HOW.
 *                  The controller only knows this interface — it doesn't
 *                  care which class actually implements it.
 *
 * - POLYMORPHISM : Any class that implements this interface can be
 *                  injected. You could swap AppointmentServiceImpl
 *                  with a mock in tests — the controller wouldn't change.
 */
public interface IAppointmentService {

    // CREATE
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto);

    // READ
    List<AppointmentResponseDTO> getAllAppointments();
    AppointmentResponseDTO       getAppointmentById(Long id);
    List<AppointmentResponseDTO> getByStatus(AppointmentStatus status);
    List<AppointmentResponseDTO> getByDepartment(String department);
    List<AppointmentResponseDTO> getTodaysAppointments();
    List<AppointmentResponseDTO> search(String query);

    // UPDATE
    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto);
    AppointmentResponseDTO cancelAppointment(Long id);
    AppointmentResponseDTO completeAppointment(Long id);

    // DELETE
    void deleteAppointment(Long id);

    // STATS
    Map<String, Long> getDashboardStats();
}
