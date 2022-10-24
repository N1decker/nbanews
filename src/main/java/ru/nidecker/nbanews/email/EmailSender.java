package ru.nidecker.nbanews.email;

public interface EmailSender {

    void send(String to, String email);
}
