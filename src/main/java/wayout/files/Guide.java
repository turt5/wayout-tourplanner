package wayout.files;

public class Guide {
    private String name;
    private String city;
    private String email;
    private String mobile;
    private String details;
    private String nid;
    private double hourlyCharge;
    private String availableTime;
    private String status;

    public Guide(String name, String city, String email, String mobile, String details, String nid, double hourlyCharge, String availableTime, String status) {
        this.name = name;
        this.city = city;
        this.email = email;
        this.mobile = mobile;
        this.details = details;
        this.nid = nid;
        this.hourlyCharge = hourlyCharge;
        this.availableTime = availableTime;
        this.status=status;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDetails() {
        return details;
    }

    public String getNID() {
        return nid;
    }

    public double getHourlyCharge() {
        return hourlyCharge;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
