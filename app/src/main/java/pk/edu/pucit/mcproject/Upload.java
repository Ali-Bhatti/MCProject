package pk.edu.pucit.mcproject;

class Upload {
    private String placeName;
    private String ImageUrl;
    private String category;
    private String userName;
    private String userEmail;
    private String aboutPlace;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String placeName, String imageUrl, String Category, String UserNAme, String UserEmail, String about_place) {
        if (placeName.trim().equals("")) {
            placeName = "No Name";
        }
        this.placeName = placeName;
        ImageUrl = imageUrl;
        category = Category;
        userName = UserNAme;
        userEmail = UserEmail;
        aboutPlace = about_place;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setName(String name) {
        placeName = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String Category) {
        category = Category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String Email) {
        userEmail = Email;
    }

    public String getAboutPlace() {
        return aboutPlace;
    }

    public void setAboutPlace(String aboutPlace) {
        this.aboutPlace = aboutPlace;
    }
}
