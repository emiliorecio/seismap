package com.seismap.service.impl;

public class CoordinatesConverter {

	public class LatitudeLongitudePosition {

		private double latitude;
		private double longitude;

		private LatitudeLongitudePosition(double latitude, double longitude,
				SphericalMercatorPosition sphericalMercatorPosition) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		private SphericalMercatorPosition sphericalMercatorPosition = null;

		public SphericalMercatorPosition getSphericalMercatorPosition() {
			if (sphericalMercatorPosition == null) {
				sphericalMercatorPosition = latitudeLongitudeToSphericalMercator(this);
			}
			return sphericalMercatorPosition;
		}

	}

	public class PixelPosition {

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

	public class SphericalMercatorPosition {

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

		private SphericalMercatorBounds sphericalMercatorBounds = null;

		public SphericalMercatorBounds getSphericalMercatorBounds() {
			if (sphericalMercatorBounds == null) {
				sphericalMercatorBounds = tileShpericalMercatorBounds(this);
			}
			return sphericalMercatorBounds;
		}
	}

	public class GoogleTilePosition {

		private int x;
		private int y;
		private int zoom;

		private GoogleTilePosition(int x, int y, int zoom,
				TmsTilePosition tmsTilePosition) {
			this.x = x;
			this.y = y;
			this.zoom = zoom;
			this.tmsTilePosition = tmsTilePosition;
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

		public SphericalMercatorBounds getSphericalMercatorBounds() {
			return getTmsTilePosition().getSphericalMercatorBounds();
		}
	}

	public class LatitudeLongitudeBounds {
		private LatitudeLongitudePosition max;
		private LatitudeLongitudePosition min;

		private LatitudeLongitudeBounds(LatitudeLongitudePosition min,
				LatitudeLongitudePosition max) {
			this.min = min;
			this.max = max;
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

	public class SphericalMercatorBounds {
		private SphericalMercatorPosition max;
		private SphericalMercatorPosition min;

		private SphericalMercatorBounds(SphericalMercatorPosition min,
				SphericalMercatorPosition max) {
			this.min = min;
			this.max = max;
		}

		public SphericalMercatorPosition getMax() {
			return max;
		}

		public SphericalMercatorPosition getMin() {
			return min;
		}

		public double getMinY() {
			return min.getY();
		}

		public double getMinX() {
			return min.getX();
		}

		public double getMaxY() {
			return max.getY();
		}

		public double getMaxX() {
			return max.getX();
		}

	}

	private static final int EARTH_RADIUS = 6378137;
	private int tileSize;
	private double originShift;
	private double initialResolution;

	public CoordinatesConverter(int tileSize) {
		// "Initialize the TMS Global Mercator pyramid"
		this.tileSize = tileSize;
		this.initialResolution = 2 * Math.PI * EARTH_RADIUS / this.tileSize;
		// 156543.03392804062 for tileSize 256 pixels
		this.originShift = 2 * Math.PI * EARTH_RADIUS / 2.0;
		// 20037508.342789244
	}

	public GoogleTilePosition createGoogleTilePosition(int x, int y, int zoom) {
		return new GoogleTilePosition(x, y, zoom, null);
	}

	public TmsTilePosition createTmsTilePosition(int x, int y, int zoom) {
		return new TmsTilePosition(x, y, zoom);
	}

	public LatitudeLongitudePosition createLatitudeLongitudePosition(
			double latitude, double longitude) {
		return new LatitudeLongitudePosition(latitude, longitude, null);
	}

	private SphericalMercatorPosition latitudeLongitudeToSphericalMercator(
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

	private LatitudeLongitudePosition sphericalMercatorToLatitudeLongitude(
			SphericalMercatorPosition sphericalMercatorPosition) {
		// "Converts XY point from Spherical Mercator EPSG:900913 to lat/lon in WGS84 Datum"
		double mx = sphericalMercatorPosition.getX();
		double my = sphericalMercatorPosition.getY();

		double lon = (mx / this.originShift) * 180.0;
		double lat = (my / this.originShift) * 180.0;

		lat = 180
				/ Math.PI
				* (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2.0);
		return new LatitudeLongitudePosition(lat, lon,
				sphericalMercatorPosition);
	}

	private SphericalMercatorPosition pixelToSphericalMercator(
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

	private PixelPosition sphericalMercatorToPixel(
			SphericalMercatorPosition sphericalMercatorPosition, int zoom) {
		// "Converts EPSG:900913 to pyramid pixel coordinates in given zoom level"
		double mx = sphericalMercatorPosition.getX();
		double my = sphericalMercatorPosition.getY();

		double res = this.resolution(zoom);
		int px = (int) ((mx + this.originShift) / res);
		int py = (int) ((my + this.originShift) / res);
		return new PixelPosition(px, py, zoom);
	}

	private TmsTilePosition pixelToTile(PixelPosition pixelPosition) {
		// "Returns a tmsTilePosition covering region in given pixel coordinates"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();
		int zoom = pixelPosition.getZoom();

		int tx = (int) (Math.ceil(px / (float) this.tileSize) - 1);
		int ty = (int) (Math.ceil(py / (float) this.tileSize) - 1);
		return new TmsTilePosition(tx, ty, zoom);
	}

	private PixelPosition pixelsToRaster(PixelPosition pixelPosition) {
		// "Move the origin of pixel coordinates to top-left corner"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();
		int zoom = pixelPosition.getZoom();

		int mapSize = this.tileSize << zoom;
		return new PixelPosition(px, mapSize - py, zoom);
	}

	private TmsTilePosition SphericalMercatorToTile(
			SphericalMercatorPosition sphericalMercatorPosition, int zoom) {
		// "Returns tmsTilePosition for given mercator coordinates"

		PixelPosition pixelPosition = this.sphericalMercatorToPixel(
				sphericalMercatorPosition, zoom);
		return this.pixelToTile(pixelPosition);
	}

	private SphericalMercatorBounds tileShpericalMercatorBounds(
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
		return new SphericalMercatorBounds(new SphericalMercatorPosition(min
				.getX(), min.getY()), new SphericalMercatorPosition(max.getX(),
				max.getY()));
	}

	private LatitudeLongitudeBounds tileLatitudeLongitudeBounds(
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

	private double resolution(int zoom) {
		// "Resolution (meters/pixel) for given zoom level (measured at Equator)"

		// s return (2 * math.pi * 6378137) / (self.tileSize * 2**zoom)
		return this.initialResolution / (Math.pow(2, zoom));
	}

	private int zoomForPixelSize(int pixelSize) {
		// "Maximal scaledown zoom of the pyramid closest to the pixelSize."

		for (int i = 0; i < 30; i++) {
			if (pixelSize > this.resolution(i)) {
				return i != 0 ? i - 1 : 0; // We don't want to scale up
			}
		}
		return 0;
	}

	private GoogleTilePosition tileToGoogleTile(TmsTilePosition tilePosition) {
		// "Converts TMS tmsTilePosition coordinates to Google Tile coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();
		int zoom = tilePosition.getZoom();
		// # coordinate origin is moved from bottom-left to top-left corner of
		// the extent
		return new GoogleTilePosition(tx, ((int) Math.pow(2, zoom) - 1) - ty,
				zoom, tilePosition);
	}

	private TmsTilePosition googleTileToTile(GoogleTilePosition tilePosition) {
		// "Converts Google tmsTilePosition coordinates to  TMS Tile coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();
		int zoom = tilePosition.getZoom();

		// # coordinate origin is moved from bottom-left to top-left corner of
		// the extent
		return new TmsTilePosition(tx, ((int) Math.pow(2, zoom) - 1) - ty, zoom);
	}

}
