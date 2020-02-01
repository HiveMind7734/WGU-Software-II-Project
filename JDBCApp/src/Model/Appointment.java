/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 *
 * @author Brian
 */
public class Appointment {
    private int id;
    private int customerId;
    private String title;
    private String description;
    private String lcoation;
    private String contact;
    private String type;
    private String url;
    //private String start;
    //private String end;
    private LocalDateTime start;
    private LocalDateTime end;
  //  private LocalDateTime date;
    
    private String createDate;
    private String createdBy;
    private String lastUpdateBy;

    public Appointment(int id, int customerId, String title, String description, String lcoation, String contact, String type, String url, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.lcoation = lcoation;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
    }

    public Appointment(int customerId, String title, String type, LocalDateTime start, LocalDateTime end) {
        this.customerId = customerId;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
    }
    
       public Appointment(int id, int customerId, String title, String type, LocalDateTime start, LocalDateTime end) {
        this.customerId = customerId;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
        this.id =id;
    }
    
    



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLcoation() {
        return lcoation;
    }

    public void setLcoation(String lcoation) {
        this.lcoation = lcoation;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getStart() {
        return start;
    }
    
    
 
    public String getStartDisplay() {
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("hh:mm a");
        
        return DTF.format(start);
    }
    
    public String getEndDisplay() {
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("hh:mm a");
        return DTF.format(end);
        
    }
    
    public String getZDTEndDisplay(){
         DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
         DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm");
         return end.format(timeDTF).toString();   
         
         // return DTF.format(end.format(timeDTF).toString());
           
    }
    
    public LocalDate getDateDisplay() {
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String dateString = DTF.format(start);
        LocalDate localDateObj = LocalDate.parse(dateString, DTF);
      return localDateObj;
        
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    
}
