package com.clinic.util;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@FacesConverter(value = "genericConverter", managed = true)
public class GenericConverter implements Converter<Object> {

    @PersistenceContext(unitName = "ClinicPU")
    private EntityManager em;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty() || value.equals("null")) return null;
        Class<?> type = component.getValueExpression("value").getType(context.getELContext());

        try {
            return em.find(type, Long.valueOf(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) return "";

        try {
            Object id = value.getClass().getMethod("getId").invoke(value);
            return String.valueOf(id);
        } catch (Exception e) {
            return "";
        }
    }
}