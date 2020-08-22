package com.ecommerce.garmentstore;

public class ItemDetails {
    public String ItemId;
    public String ItemName;
    public String Size;
    public String Company;
    public String Rate;
    public String color;
    public String Quantity;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
