module com.cate.typingthedictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.cate.typingthedictionary to javafx.fxml;
    exports com.cate.typingthedictionary;
    exports com.cate.typingthedictionary.Controllers;
    opens com.cate.typingthedictionary.Controllers to javafx.fxml;
}