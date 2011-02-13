package com.seismap.service.impl;

public class MercatorCoordinatesConverter {

	public class LatitudeLongitudePosition {

		private double latitude;
		private double longitude;

		private LatitudeLongitudePosition(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}
	}

	private class PixelPosition {

		private int x;
		private int y;
		private int zoom;

		private PixelPosition(int x, int y, int zoom) {
			this.x = x;
			this.y = y;
			this.zoom = zoom;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZoom() {
			return zoom;
		}

	}

	private class SphericalMercatorPosition {

		private double x;
		private double y;

		private SphericalMercatorPosition(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

	}

	public class TmsTilePosition {

		private int x;
		private int y;
		private int zoom;

		private TmsTilePosition(int x, int y, int zoom) {
			this.x = x;
			this.y = y;
			this.zoom = zoom;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZoom() {
			return zoom;
		}

		private LatitudeLongitudeBounds latitudeLongitudeBounds = null;

		public LatitudeLongitudeBounds getLatitudeLongitudeBounds() {
			if (latitudeLongitudeBounds == null) {
				latitudeLongitudeBounds = tileLatitudeLongitudeBounds(this);
			}
			return latitudeLongitudeBounds;
		}
	}

	public class GoogleTilePosition {

		private int x;
		private int y;
		private int zoom;

		private GoogleTilePosition(int x, int y, int zoom) {
			this.x = x;
			this.y = y;
			this.zoom = zoom;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZoom() {
			return zoom;
		}

		private TmsTilePosition tmsTilePosition = null;

		public TmsTilePosition getTmsTilePosition() {
			if (tmsTilePosition == null) {
				tmsTilePosition = googleTileToTile(this);
			}
			return tmsTilePosition;
		}

		public LatitudeLongitudeBounds getLatitudeLongitudeBounds() {
			return getTmsTilePosition().getLatitudeLongitudeBounds();
		}
	}

	public class LatitudeLongitudeBounds {
		private LatitudeLongitudePosition max;
		private LatitudeLongitudePosition min;

		private LatitudeLongitudeBounds(LatitudeLongitudePosition max,
				LatitudeLongitudePosition min) {
			this.max = max;
			this.min = min;
		}

		private LatitudeLongitudeBounds(double minLatitude,
				double minLongitude, double maxLatitude, double maxLongitude) {
			this(new LatitudeLongitudePosition(minLatitude, minLongitude),
					new LatitudeLongitudePosition(maxLatitude, maxLongitude));
		}

		public LatitudeLongitudePosition getMax() {
			return max;
		}

		public LatitudeLongitudePosition getMin() {
			return min;
		}

		public double getMinLatitude() {
			return min.getLatitude();
		}

		public double getMinLongitude() {
			return min.getLongitude();
		}

		public double getMaxLatitude() {
			return max.getLatitude();
		}

		public double getMaxLongitude() {
			return max.getLongitude();
		}
	}

	private class SphericalMercatorBounds {
		private SphericalMercatorPosition max;
		private SphericalMercatorPosition min;

		private SphericalMercatorBounds(SphericalMercatorPosition max,
				SphericalMercatorPosition min) {
			this.max = max;
			this.min = min;
		}

		private SphericalMercatorBounds(double minLatitude,
				double minLongitude, double maxLatitude, double maxLongitude) {
			this(new SphericalMercatorPosition(minLatitude, minLongitude),
					new SphericalMercatorPosition(maxLatitude, maxLongitude));
		}

		public SphericalMercatorPosition getMax() {
			return max;
		}

		public SphericalMercatorPosition getMin() {
			return min;
		}
	}

	private static final int EARTH_RADIUS = 6378137;
	private int tileSize;
	private double originShift;
	private double initialResolution;

	public MercatorCoordinatesConverter(int tileSize) {
		// "Initialize the TMS Global Mercator pyramid"
		this.tileSize = tileSize;
		this.initialResolution = 2 * Math.PI * EARTH_RADIUS / this.tileSize;
		// 156543.03392804062 for tileSize 256 pixels
		this.originShift = 2 * Math.PI * EARTH_RADIUS / 2.0;
		// 20037508.342789244
	}

	public GoogleTilePosition createGoogleTilePosition(int x, int y, int zoom) {
		return new GoogleTilePosition(x, y, zoom);
	}

	public TmsTilePosition createTmsTilePosition(int x, int y, int zoom) {
		return new TmsTilePosition(x, y, zoom);
	}

	public SphericalMercatorPosition latitudeLongitudeToSphericalMercator(
			LatitudeLongitudePosition latitudeLongitude) {
		// "Converts given lat/lon in WGS84 Datum to XY in Spherical Mercator EPSG:900913"
		double lat = latitudeLongitude.getLatitude();
		double lon = latitudeLongitude.getLongitude();

		double mx = lon * this.originShift / 180.0;
		double my = Math.log(Math.tan((90 + lat) * Math.PI / 360.0))
				/ (Math.PI / 180.0);

		my = my * this.originShift / 180.0;
		return new SphericalMercatorPosition(mx, my);
	}

	public LatitudeLongitudePosition sphericalMercatorToLatitudeLongitude(
			SphericalMercatorPosition sphericalMercatorPosition) {
		// "Converts XY point from Spherical Mercator EPSG:900913 to lat/lon in WGS84 Datum"
		double mx = sphericalMercatorPosition.getX();
		double my = sphericalMercatorPosition.getY();

		double lon = (mx / this.originShift) * 180.0;
		double lat = (my / this.originShift) * 180.0;

		lat = 180
				/ Math.PI
				* (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2.0);
		return new LatitudeLongitudePosition(lat, lon);
	}

	public SphericalMercatorPosition pixelToSphericalMercator(
			PixelPosition pixelPosition) {
		// "Converts pixel coordinates in given zoom level of pyramid to EPSG:900913"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();
		int zoom = pixelPosition.getZoom();

		double res = this.resolution(zoom);
		double mx = px * res - this.originShift;
		double my = py * res - this.originShift;
		return new SphericalMercatorPosition(mx, my);
	}

	public PixelPosition sphericalMercatorToPixel(
			SphericalMercatorPosition sphericalMercatorPosition, int zoom) {
		// "Converts EPSG:900913 to pyramid pixel coordinates in given zoom level"
		double mx = sphericalMercatorPosition.getX();
		double my = sphericalMercatorPosition.getY();

		double res = this.resolution(zoom);
		int px = (int) ((mx + this.originShift) / res);
		int py = (int) ((my + this.originShift) / res);
		return new PixelPosition(px, py, zoom);
	}

	public TmsTilePosition pixelToTile(PixelPosition pixelPosition) {
		// "Returns a tmsTilePosition covering region in given pixel coordinates"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();
		int zoom = pixelPosition.getZoom();

		int tx = (int) (Math.ceil(px / (float) this.tileSize) - 1);
		int ty = (int) (Math.ceil(py / (float) this.tileSize) - 1);
		return new TmsTilePosition(tx, ty, zoom);
	}

	public PixelPosition pixelsToRaster(PixelPosition pixelPosition) {
		// "Move the origin of pixel coordinates to top-left corner"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();
		int zoom = pixelPosition.getZoom();

		int mapSize = this.tileSize << zoom;
		return new PixelPosition(px, mapSize - py, zoom);
	}

	public TmsTilePosition SphericalMercatorToTile(
			SphericalMercatorPosition sphericalMercatorPosition, int zoom) {
		// "Returns tmsTilePosition for given mercator coordinates"

		PixelPosition pixelPosition = this.sphericalMercatorToPixel(
				sphericalMercatorPosition, zoom);
		return this.pixelToTile(pixelPosition);
	}

	public SphericalMercatorBounds tileShpericalMercatorBounds(
			TmsTilePosition tilePosition) {
		// "Returns bounds of the given tmsTilePosition in EPSG:900913 coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();
		int zoom = tilePosition.getZoom();

		SphericalMercatorPosition min = this
				.pixelToSphericalMercator(new PixelPosition(tx * this.tileSize,
						ty * this.tileSize, zoom));
		SphericalMercatorPosition max = this
				.pixelToSphericalMercator(new PixelPosition((tx + 1)
						* this.tileSize, (ty + 1) * this.tileSize, zoom));
		return new SphericalMercatorBounds(min.getX(), min.getY(), max.getX(),
				max.getY());
	}

	public LatitudeLongitudeBounds tileLatitudeLongitudeBounds(
			TmsTilePosition tilePosition) {
		// "Returns bounds of the given tmsTilePosition in latutude/longitude using WGS84 datum"

		SphericalMercatorBounds bounds = this
				.tileShpericalMercatorBounds(tilePosition);
		LatitudeLongitudePosition min = this
				.sphericalMercatorToLatitudeLongitude(bounds.getMin());
		LatitudeLongitudePosition max = this
				.sphericalMercatorToLatitudeLongitude(bounds.getMax());

		return new LatitudeLongitudeBounds(min, max);
	}

	public double resolution(int zoom) {
		// "Resolution (meters/pixel) for given zoom level (measured at Equator)"

		// s return (2 * math.pi * 6378137) / (self.tileSize * 2**zoom)
		return this.initialResolution / (Math.pow(2, zoom));
	}

	public int zoomForPixelSize(int pixelSize) {
		// "Maximal scaledown zoom of the pyramid closest to the pixelSize."

		for (int i = 0; i < 30; i++) {
			if (pixelSize > this.resolution(i)) {
				return i != 0 ? i - 1 : 0; // We don't want to scale up
			}
		}
		return 0;
	}

	public GoogleTilePosition tileToGoogleTile(TmsTilePosition tilePosition) {
		// "Converts TMS tmsTilePosition coordinates to Google Tile coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();
		int zoom = tilePosition.getZoom();
		// # coordinate origin is moved from bottom-left to top-left corner of
		// the extent
		return new GoogleTilePosition(tx, ((int)Math.pow(2, zoom) - 1) - ty, zoom);
	}

	public TmsTilePosition googleTileToTile(GoogleTilePosition tilePosition) {
		// "Converts Google tmsTilePosition coordinates to  TMS Tile coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();
		int zoom = tilePosition.getZoom();

		// # coordinate origin is moved from bottom-left to top-left corner of
		// the extent
		return new TmsTilePosition(tx, ((int)Math.pow(2, zoom) - 1) - ty, zoom);
	}

}
