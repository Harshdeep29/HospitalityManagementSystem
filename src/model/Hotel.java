package model;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private String amenities;
    private String phone;

    public Hotel() {}

    public Hotel(int id, String name, String address, String amenities, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.amenities = amenities;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", amenities='" + amenities + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
