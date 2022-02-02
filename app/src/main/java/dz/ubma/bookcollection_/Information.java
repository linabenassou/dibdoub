package dz.ubma.bookcollection_;

import android.util.EventLogTags;

public class Information {
    //String image;
    String Titre;



    int iconStar ;


   String Description;
    String id;
    String Year;
public Information(){}
public Information(String id, String Titre, String Description,String Year){//,String image// , String Description,){
        this.id=id;
        this.Titre=Titre;
        this.Description=Description;
        this.Year=Year;}
public Information(String id, String Titre, String Description,String Year,int iconStar){//,String image// , String Description,){
    this.id=id;
    this.Titre=Titre;
    this.Description=Description;
    this.Year=Year;
    this.iconStar = iconStar;

}  public int getIconStar() {
        return iconStar;
    }

    public void setIconStar(int iconStar) {
        this.iconStar = iconStar;
    }
   /* public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
*/   public String getId() {
       return id;
   }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

  public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }



    }

