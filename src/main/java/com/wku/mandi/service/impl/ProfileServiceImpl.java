package com.wku.mandi.service.impl;

import com.wku.mandi.dao.UserDao;
import com.wku.mandi.db.Address;
import com.wku.mandi.db.User;
import com.wku.mandi.db.Vault;
import com.wku.mandi.rest.GeographicalAPI;
import com.wku.mandi.rest.ZipcodeRestAPI;
import com.wku.mandi.rest.response.GeospatialAPIResponse;
import com.wku.mandi.rest.response.ZipCodeResponse;
import com.wku.mandi.service.ProfileService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by srujangopu on 7/5/15.
 */

@Service("ProfileService")
public class ProfileServiceImpl implements ProfileService{

    private final UserDao userDao;
    private final ZipcodeRestAPI zipcodeRestAPI;
    private final GeographicalAPI geographicalAPI;
    
    Log log=LogFactory.getLog(ProfileServiceImpl.class);

    @Autowired
    public ProfileServiceImpl(UserDao userDao, ZipcodeRestAPI zipcodeRestAPI, GeographicalAPI geographicalAPI) {

        this.userDao = userDao;
        this.zipcodeRestAPI = zipcodeRestAPI;
        this.geographicalAPI = geographicalAPI;

    }

    @Override
    public User findUserById(String id) {
        return userDao.findUserById(id);
    }

    @Override
    public List<User> findUsersWithNameLike(String nameLike) {
        return userDao.findUsersWithNameLike(nameLike);
    }

    @Override
    public void saveUser(User user) {

        for(Address address : user.getAddresses()){
            String street1 = address.getAddressLine1();
            String street2 = address.getAddressLine2();
            String city = address.getCity();
            String state = address.getState();
            String zipCode = address.getZipCode();

            String completeAddress = String.join(" ",street1,street2,city,state,zipCode);
            GeospatialAPIResponse geospatialAPIResponse = geographicalAPI.getGeospatialAPIResponse(completeAddress);

            if(geospatialAPIResponse != null){
                getLatitudeLongitude(address, geospatialAPIResponse);
            }
        }
        userDao.saveUser(user);
    }
    
    // @TODO: Investigate a better way to extract latitude and longitude 
	private void getLatitudeLongitude(Address address, GeospatialAPIResponse geospatialAPIResponse) {
	   try{

		   double latitude = new BigDecimal(geospatialAPIResponse.getResults().get(0).getGeometry().getLocation().getLat()).doubleValue();
           double longitude = new BigDecimal(geospatialAPIResponse.getResults().get(0).getGeometry().getLocation().getLng()).doubleValue();
           double[] location = {longitude, latitude};
           
           address.setLocation(location);
	   }
	   catch(NullPointerException exception) {
		   log.error("Got exception while extracting Latitude and longitude ", exception);
	   }
	}
	
	@Override
	public boolean saveRegistrationInfo(Vault vault) {
		return userDao.saveRegistraionInfo(vault);
	}

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User deleteUser(String id) {
        return userDao.deleteUser(id);
    }

    @Override
    public void uploadProfileImage(String id, InputStream profileImage) {
          userDao.uploadProfileImage(id, profileImage);
    }

    @Override
    public ZipCodeResponse getAddressDetails(String zipCode) {
        return zipcodeRestAPI.getAddressDetails(zipCode);
    }

    @Override
    public List<User> searchResults(String zipCode, int distance) {

        ZipCodeResponse zipCodeResponse = this.zipcodeRestAPI.getAddressDetails(zipCode);
        double latitude = new BigDecimal(zipCodeResponse.getPlaces().get(0).getLatitude()).doubleValue();
        double longitude = new BigDecimal(zipCodeResponse.getPlaces().get(0).getLongitude()).doubleValue();
        double[] location = {longitude, latitude};
        return this.userDao.getSearchResults(location,distance);
    }

}
