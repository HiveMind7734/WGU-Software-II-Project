/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.ObservableList;

/**
 *
 * @author Brian
 */
public interface Loop {
    ObservableList<Appointment> loop(ObservableList<Appointment> apt);
}
