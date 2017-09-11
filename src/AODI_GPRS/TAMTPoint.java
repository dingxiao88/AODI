package AODI_GPRS;

import java.util.Date;

public class TAMTPoint {

	private String id; // the id of this point
	private String gpsTraceId; // parent (owner) id of this point
	private double latitude; // decimal degrees WGS84
	private double longitude;  // decimal degrees WGS84
	private double bearing; // 0 - 360 degrees
	private double speed; // unit is now meters per second
	private double altitude; // altitude
	private String altitudeUnits; // units for altitude
	private String timestamp; // yyyy/MM/dd HH:mm:ss for easy UTC conversion
	
	public TAMTPoint()
	{
		
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setGpsTraceId(String gpsTraceId) {
		this.gpsTraceId = gpsTraceId;
	}

	public String getGpsTraceId() {
		return gpsTraceId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public void setAltitudeUnits(String altitudeUnits) {
		this.altitudeUnits = altitudeUnits;
	}

	public String getAltitudeUnits() {
		return altitudeUnits;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TAMTPoint [altitude=");
		builder.append(altitude);
		builder.append(", altitudeUnits=");
		builder.append(altitudeUnits);
		builder.append(", bearing=");
		builder.append(bearing);
		builder.append(", gpsTraceId=");
		builder.append(gpsTraceId);
		builder.append(", id=");
		builder.append(id);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append("]");
		return builder.toString();
	}

}
