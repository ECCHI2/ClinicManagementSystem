package com.clinic.bean;

import com.clinic.entity.Invoice;
import com.clinic.entity.Patient;
import com.clinic.entity.Appointment;
import com.clinic.facadeLocal.InvoiceFacadeLocal;
import com.clinic.facadeLocal.PatientFacadeLocal;
import com.clinic.facadeLocal.AppointmentFacadeLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class InvoiceBean implements Serializable {

    private Invoice invoice;
    private List<Invoice> invoices;
    private Long patientId;
    private Long appointmentId;

    @EJB
    private InvoiceFacadeLocal invoiceFacade;
    @EJB
    private PatientFacadeLocal patientFacade;
    @EJB
    private AppointmentFacadeLocal appointmentFacade;

    @PostConstruct
    public void init() {
        resetInvoice();
        loadInvoices();
    }

    public void loadInvoices() {
        this.invoices = invoiceFacade.findAll();
    }

    public List<Patient> getAllPatients() { return patientFacade.findAll(); }
    public List<Appointment> getAllAppointments() { return appointmentFacade.findAll(); }

    public void save() {
        try {
            if (patientId == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Lütfen فواتير المريض تطلب تحديد مريض أولاً!");
                return;
            }

            invoice.setPatient(patientFacade.find(patientId));
            if (appointmentId != null) {
                invoice.setAppointment(appointmentFacade.find(appointmentId));
            } else {
                invoice.setAppointment(null);
            }

            if (invoice.getId() == null) {
                invoiceFacade.create(invoice);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Fatura başarıyla oluşturuldu.");
            } else {
                invoiceFacade.edit(invoice);
                addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Fatura başarıyla güncellendi.");
            }

            resetInvoice();
            loadInvoices();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Fatura kaydedilirken bir hata oluştu: " + e.getMessage());
        }
    }

    public void delete(Invoice i) {
        try {
            invoiceFacade.remove(i);
            loadInvoices();
            addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Fatura başarıyla silindi.");
        } catch (Exception e) {
            Throwable t = e.getCause();
            boolean isConstraintViolation = false;

            while (t != null) {
                if (t.getMessage() != null && (t.getMessage().toLowerCase().contains("constraint") || t.getMessage().toLowerCase().contains("foreign key"))) {
                    isConstraintViolation = true;
                    break;
                }
                t = t.getCause();
            }

            if (isConstraintViolation) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu fatura silinemez! Çünkü sistemde bu faturayla ilişkili aktif muhasebe veya raporlama kayıtları bulunmaktadır.");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Silme işlemi başarısız oldu.");
            }
        }
    }

    public void prepareEdit(Invoice i) {
        this.invoice = i;
        this.patientId = (i.getPatient() != null) ? i.getPatient().getId() : null;
        this.appointmentId = (i.getAppointment() != null) ? i.getAppointment().getId() : null;
    }

    private void resetInvoice() {
        invoice = new Invoice();
        invoice.setInvoiceDate(new java.util.Date());
        patientId = null;
        appointmentId = null;
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters and Setters
    public Invoice getInvoice() { return invoice; }
    public void setInvoice(Invoice invoice) { this.invoice = invoice; }
    public List<Invoice> getInvoices() { return invoices; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
}