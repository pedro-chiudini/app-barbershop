package com.pedrochiudini.app_barbershop.util;

public enum StatusSchedules {
    
    CONFIRMADO("confirmado"),
    CONCLUIDO("concluido"),
    CANCELADO("cancelado");

    private String status;

    StatusSchedules(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
