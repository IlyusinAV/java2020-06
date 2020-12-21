package ru.otus;

import java.util.Objects;

public class MyMessage extends Message {
    private final String field1;
    private final String field2;
    private final String field3;
    private final String field4;
    private final String field5;
    private final String field6;
    private final String field7;
    private final String field8;
    private final String field9;
    private final String field10;
    private final String field11;
    private final String field12;
    private final ObjectForMessage field13;

    private MyMessage(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, ObjectForMessage field13) {
        super();
        this.field11 = field11;
        this.field12 = field12;
        this.field13 = field13;
    }

    public String getField11() {
        return field11;
    }

    public String getField12() {
        return field12;
    }

    public ObjectForMessage getField13() {
        return field13;
    }

    @Override
    public boolean equals(Object o) {
        if (field11 == null && field12 == null && field13 == null {
            return super.equals(o);
        } else{
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MyMessage message = (MyMessage) o;

            if (!Objects.equals(field1, message.field1)) return false;
            if (!Objects.equals(field2, message.field2)) return false;
            if (!Objects.equals(field3, message.field3)) return false;
            if (!Objects.equals(field4, message.field4)) return false;
            if (!Objects.equals(field5, message.field5)) return false;
            if (!Objects.equals(field6, message.field6)) return false;
            if (!Objects.equals(field7, message.field7)) return false;
            if (!Objects.equals(field8, message.field8)) return false;
            if (!Objects.equals(field9, message.field9)) return false;
            if (!Objects.equals(field10, message.field10)) return false;
            if (!Objects.equals(field11, message.field11)) return false;
            if (!Objects.equals(field12, message.field12)) return false;
            return Objects.equals(field13, message.field13);
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (field11 != null ? field11.hashCode() : 0);
        result = 31 * result + (field13 != null ? field13.hashCode() : 0);
        result = 31 * result + (field13 != null ? field13.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4='" + field4 + '\'' +
                ", field5='" + field5 + '\'' +
                ", field6='" + field6 + '\'' +
                ", field7='" + field7 + '\'' +
                ", field8='" + field8 + '\'' +
                ", field9='" + field9 + '\'' +
                ", field10='" + field10 + '\'' +
                ", field11='" + field11 + '\'' +
                ", field12='" + field11 + '\'' +
                ", field13='" + field13 + '\'' +
                '}';
    }

    public MyBuilder toBuilder() {
        return new MyBuilder(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    public static class MyBuilder extends Builder {
        private String field1;
        private String field2;
        private String field3;
        private String field4;
        private String field5;
        private String field6;
        private String field7;
        private String field8;
        private String field9;
        private String field10;
        private String field11;
        private String field12;
        private ObjectForMessage field13;

        public MyBuilder() {
        }

        private MyBuilder(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, ObjectForMessage field13) {
        }

        public MyBuilder field1(String field1) {
            this.field1 = field1;
            return this;
        }

        public MyBuilder field2(String field2) {
            this.field2 = field2;
            return this;
        }

        public MyBuilder field3(String field3) {
            this.field3 = field3;
            return this;
        }

        public MyBuilder field4(String field4) {
            this.field4 = field4;
            return this;
        }

        public MyBuilder field5(String field5) {
            this.field5 = field5;
            return this;
        }

        public MyBuilder field6(String field6) {
            this.field6 = field6;
            return this;
        }

        public MyBuilder field7(String field7) {
            this.field7 = field7;
            return this;
        }

        public MyBuilder field8(String field8) {
            this.field8 = field8;
            return this;
        }

        public MyBuilder field9(String field9) {
            this.field9 = field9;
            return this;
        }

        public MyBuilder field10(String field10) {
            this.field10 = field10;
            return this;
        }

        public MyBuilder field11(String field11) {
            this.field11 = field11;
            return this;
        }

        public MyBuilder field12(String field12) {
            this.field12 = field12;
            return this;
        }

        public MyBuilder field13(String field13) {
            this.field13 = field13;
            return this;
        }

        public MyMessage build() {
            return new MyMessage(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
        }
    }
}