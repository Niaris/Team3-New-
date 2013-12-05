package com.team3.business;

import java.util.ArrayList;
import java.util.List;
import com.team3.dataaccess.MySQLConnection;
import com.team3.entities.LocationVO;


public class LocationBusiness {
        
        private MySQLConnection DBConnection;

        public LocationBusiness(MySQLConnection mySQLConnection) {
                this.DBConnection = mySQLConnection;
        }
        
        public void validateLocation(LocationVO location) throws Exception {
                if(location.getAddress().isEmpty() || location.getName().isEmpty()) {
                        throw new Exception("Address and Name are mandatory fields");
                }
        }

        public List<LocationVO> retrieveLocationsByUserPosition (double latitude, double longitude) {
                return DBConnection.retrieveLocationsNearByUser(latitude, longitude);
        }
        
}

