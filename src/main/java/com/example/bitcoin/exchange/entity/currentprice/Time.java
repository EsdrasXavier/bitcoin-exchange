package com.example.bitcoin.exchange.entity.currentprice;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class Time {
    // Jul 28, 2021 09:48:00 UTC
    // MMM d, yyyy HH:mm:ss z
    @JsonFormat(shape = Shape.STRING, pattern = "MMM d, yyyy HH:mm:ss z")
    private LocalDateTime updated;

    // updatedISO: "2021-07-28T09:48:00+00:00"
    //              yyyyy-MM-dd'T'HH:mm:ss+0000
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss+00:00")
    private LocalDateTime updatedISO;

    // updateduk: "Jul 28, 2021 at 10:48 BST"
    @JsonFormat(shape = Shape.STRING, pattern = "MMM d, yyyy 'at' HH:mm z")
    private LocalDateTime updateduk;

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(LocalDateTime updatedISO) {
        this.updatedISO = updatedISO;
    }

    public LocalDateTime getUpdateduk() {
        return updateduk;
    }

    public void setUpdateduk(LocalDateTime updateduk) {
        this.updateduk = updateduk;
    }

}
