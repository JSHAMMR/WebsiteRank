package tmtest.tm.com.testtm;


import android.support.annotation.NonNull;

public class WebsiteModel  implements Comparable{
    int id_website;
    String website_name;
    String visit_date;
    int total_visits;


    public WebsiteModel( int id_website,
                         String website_name,
                         String visit_date,
                         int total_visits) {
        this.id_website = id_website;
        this.website_name = website_name;
        this.visit_date = visit_date;
        this.total_visits =total_visits;

    }


    public int getId_website() {
        return id_website;
    }
    public String getWebsite_name() {
        return website_name;
    }
    public String getVisit_date() {
        return visit_date;
    }
    public int getTotal_visits() {
        return total_visits;
    }


    @Override
    public int compareTo(@NonNull Object o) {

        WebsiteModel websiteModel =(WebsiteModel) o;
        int compareVisit =websiteModel.getTotal_visits();
        /* For Ascending order*/
        return this.total_visits-compareVisit;

    }
}




