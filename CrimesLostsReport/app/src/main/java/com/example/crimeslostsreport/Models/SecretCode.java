package com.example.crimeslostsreport.Models;

public class SecretCode {

    public int secretcodeId;
    public String secretcode;

    public SecretCode(int secretcodeId, String secretcode) {
        this.secretcodeId = secretcodeId;
        this.secretcode = secretcode;
    }

    public SecretCode() {
    }

    public int getSecretcodeId() {
        return secretcodeId;
    }

    public void setSecretcodeId(int secretcodeId) {
        this.secretcodeId = secretcodeId;
    }

    public String getSecretcode() {
        return secretcode;
    }

    public void setSecretcode(String secretcode) {
        this.secretcode = secretcode;
    }
}
