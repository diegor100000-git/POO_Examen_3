package pe.edu.upeu.sistemabiblioteca.exception;

import lombok.Getter;

    @Getter
    public class ModelNotFoundException extends RuntimeException{
        private final int errorCode;
        public ModelNotFoundException(String message) {
            super(message);
            this.errorCode = 0; // 0 = código genérico
        }
        public ModelNotFoundException(String message, int errorCode) {
            super(message);
            this.errorCode = errorCode;
        }
    }
