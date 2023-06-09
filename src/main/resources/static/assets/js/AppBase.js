class AppBase {
    static DOMAIN_SERVER = location.origin;

    static API_SERVER = this.DOMAIN_SERVER + '/api';

    static API_AUTH = this.API_SERVER + '/auth';

    static API_CUSTOMER = this.API_SERVER + '/customers';
    static API_PRODUCT = this.API_SERVER + '/products';

    static API_DEPOSIT = this.API_CUSTOMER + '/deposit';

    static API_TRANSFER = this.API_SERVER + '/transfers';

    static API_PROVINCE = "https://vapi.vnappmob.com/api/province";

    static API_CLOUDINARY = 'https://res.cloudinary.com/dea7a6fgj/image/upload';

    static SCALE_IMAGE_W_80_H_80_Q_100 = 'c_limit,w_80,h_80,q_100';
    static SCALE_IMAGE_W600_H850_Q100 = "c_limit,w_600,h_850,q_100";
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class CustomerAvatar {
    constructor(id, fileFolder, fileName, fileUrl) {
        this.id = id;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}

class Customer {
    constructor(id, customerAvatar, fullName, email, phone, locationRegion, balance) {
        this.id = id;
        this.customerAvatar = customerAvatar;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion;
        this.balance = balance;
    }
}
class Product{
    constructor(id, title, price, description, quantity, productAvatar, type, brand) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.productAvaratr = productAvatar;
        this.type = type;
        this.brand = brand;
    }
}
class ProductAvatar {
    constructor(id, fileFolder, fileName, fileUrl) {
        this.id = id;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}
class Type{
    contructor (id, code){
        this.id = id;
        this.code = code;
    }
}
class Brand{
    contructor (id, code){
        this.id = id;
        this.code = code;
    }
}