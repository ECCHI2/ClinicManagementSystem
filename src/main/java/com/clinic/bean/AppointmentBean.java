package com.clinic.bean;

import com.clinic.entity.Appointment;
import com.clinic.entity.AppointmentStatus;
import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.facade.DoctorFacade;
import com.clinic.facadeLocal.AppointmentFacadeLocal;
import com.clinic.facadeLocal.DoctorFacadeLocal;
import com.clinic.facadeLocal.PatientFacadeLocal;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
@Named
@ViewScoped
public class AppointmentBean implements Serializable {

    @EJB
    private AppointmentFacadeLocal appointmentFacade;
    @EJB
    private DoctorFacadeLocal doctorFacade;
    @EJB
    private PatientFacadeLocal patientFacade;

    private Appointment appointment = new Appointment();
    private List<Appointment> appointmentList;


    // دوال لجلب القوائم للقوائم المنسدلة (Dropdowns)
    public List<Doctor> getAllDoctors() {
        return doctorFacade.findAll();
    }

    public List<Patient> getAllPatients() {
        return patientFacade.findAll();
    }

    public void loadAppointments() {
        this.appointmentList = appointmentFacade.findAll();
    }

    @PostConstruct
    public void init() {
        // 1. تجهيز الكائنات الفارغة لمنع الـ NullPointerException في الـ Dropdowns
        if (appointment == null) {
            appointment = new Appointment();
        }
        if (appointment.getDoctor() == null) {
            appointment.setDoctor(new Doctor());
        }
        if (appointment.getPatient() == null) {
            appointment.setPatient(new Patient());
        }

        // 2. تحميل قائمة المواعيد من الداتابيز لتظهر في الجدول فور فتح الصفحة
        loadAppointments();
    }

// احذف دالة @PostConstruct public void inint() { loadAppointments(); } لأنها مكررة

    public void save() {
        try {
            if (appointment.getId() == null) {
                // إضافة جديد
                appointment.setStatus(AppointmentStatus.PENDING);
                appointmentFacade.create(appointment);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Yeni randevu eklendi."));
            } else {
                // تعديل موجود (Merge)
                appointmentFacade.edit(appointment);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Randevu başarıyla güncellendi."));
            }

            // تصفير الكائن بعد النجاح عشان يرجع الفورم فاضي
            appointment = new Appointment();
            appointment.setDoctor(new Doctor());
            appointment.setPatient(new Patient());
            loadAppointments();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete(Appointment a) {
        try {
            appointmentFacade.remove(a); // استدعاء دالة الحذف القوية
            loadAppointments(); // إعادة تحميل القائمة فوراً لتحديث الجدول

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Randevu silindi."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Silme işlemi başarısız!"));
            e.printStackTrace();
        }
    }

    public void prepareEdit(Appointment a) {
        // نستخدم find لضمان أن الكائن مربوط بالـ EntityManager وفيه الـ ID وكل البيانات
        this.appointment = appointmentFacade.find(a.getId());

        // تأكد أن الكائنات الداخلية مو null عشان الـ Dropdowns تشتغل صح
        if (this.appointment.getDoctor() == null) this.appointment.setDoctor(new Doctor());
        if (this.appointment.getPatient() == null) this.appointment.setPatient(new Patient());
    }

    // أضف هذا السطر داخل كلاس AppointmentBean
    public java.util.Date getCurrentDate() {
        return new java.util.Date(); // يرجع تاريخ وساعة اللحظة الحالية للمقارنة
    }

    public AppointmentStatus[] getStatuses() {
        return AppointmentStatus.values();
    }

    // Getters and Setters
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public List<Appointment> getAppointmentList() {
        if (appointmentList == null) {
            appointmentList = appointmentFacade.findAll();
        }
        return appointmentList;
    }
}