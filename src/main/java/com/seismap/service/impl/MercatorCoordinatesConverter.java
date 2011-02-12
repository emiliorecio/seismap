package com.seismap.service.impl;

public class MercatorCoordinatesConverter {

	public static class LatitudeLongitudePosition {

		private double latitude;
		private double longitude;

		public LatitudeLongitudePosition(double latitude, double longitude) {
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

	public static class PixelPosition {

		private int x;
		private int y;

		public PixelPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}

	public static class SphericalMercatorPosition {

		private double x;
		private double y;

		public SphericalMercatorPosition(double x, double y) {
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

	public static class TmsTilePosition {

		private int x;
		private int y;

		public TmsTilePosition(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}

	public static class GoogleTilePosition {

		private int x;
		private int y;

		public GoogleTilePosition(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}

	public static class QuadTreeTilePosition {

		private String key;

		public QuadTreeTilePosition(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

	}

	public static class LatitudeLongitudeBounds {
		private LatitudeLongitudePosition max;
		private LatitudeLongitudePosition min;

		public LatitudeLongitudeBounds(LatitudeLongitudePosition max,
				LatitudeLongitudePosition min) {
			this.max = max;
			this.min = min;
		}

		public LatitudeLongitudeBounds(double minLatitude, double minLongitude,
				double maxLatitude, double maxLongitude) {
			this(new LatitudeLongitudePosition(minLatitude, minLongitude),
					new LatitudeLongitudePosition(maxLatitude, maxLongitude));
		}

		public LatitudeLongitudePosition getMax() {
			return max;
		}

		public LatitudeLongitudePosition getMin() {
			return min;
		}
	}

	public static class SphericalMercatorBounds {
		private SphericalMercatorPosition max;
		private SphericalMercatorPosition min;

		public SphericalMercatorBounds(SphericalMercatorPosition max,
				SphericalMercatorPosition min) {
			this.max = max;
			this.min = min;
		}

		public SphericalMercatorBounds(double minLatitude, double minLongitude,
				double maxLatitude, double maxLongitude) {
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
			PixelPosition pixelPosition, int zoom) {
		// "Converts pixel coordinates in given zoom level of pyramid to EPSG:900913"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();

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
		return new PixelPosition(px, py);
	}

	public TmsTilePosition pixelToTmsTile(PixelPosition pixelPosition) {
		// "Returns a tile covering region in given pixel coordinates"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();

		int tx = (int) (Math.ceil(px / (float) this.tileSize) - 1);
		int ty = (int) (Math.ceil(py / (float) this.tileSize) - 1);
		return new TmsTilePosition(tx, ty);
	}

	public PixelPosition pixelsToRaster(PixelPosition pixelPosition, int zoom) {
		// "Move the origin of pixel coordinates to top-left corner"
		int px = pixelPosition.getX();
		int py = pixelPosition.getY();

		int mapSize = this.tileSize << zoom;
		return new PixelPosition(px, mapSize - py);
	}

	public TmsTilePosition SphericalMercatorToTmsTile(
			SphericalMercatorPosition sphericalMercatorPosition, int zoom) {
		// "Returns tile for given mercator coordinates"

		PixelPosition pixelPosition = this.sphericalMercatorToPixel(
				sphericalMercatorPosition, zoom);
		return this.pixelToTmsTile(pixelPosition);
	}

	public SphericalMercatorBounds tmsTileShpericalMercatorBounds(
			TmsTilePosition tilePosition, int zoom) {
		// "Returns bounds of the given tile in EPSG:900913 coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();

		SphericalMercatorPosition min = this
				.pixelToSphericalMercator(new PixelPosition(tx * this.tileSize,
						ty * this.tileSize), zoom);
		SphericalMercatorPosition max = this.pixelToSphericalMercator(
				new PixelPosition((tx + 1) * this.tileSize, (ty + 1)
						* this.tileSize), zoom);
		return new SphericalMercatorBounds(min.getX(), min.getY(), max.getX(),
				max.getY());
	}

	public LatitudeLongitudeBounds tmsTileLatitudeLongitudeBounds(
			TmsTilePosition tilePosition, int zoom) {
		// "Returns bounds of the given tile in latutude/longitude using WGS84 datum"

		SphericalMercatorBounds bounds = this.tmsTileShpericalMercatorBounds(
				tilePosition, zoom);
		LatitudeLongitudePosition min = this
				.sphericalMercatorToLatitudeLongitude(bounds.getMin());
		LatitudeLongitudePosition max = this
				.sphericalMercatorToLatitudeLongitude(bounds.getMax());

		return new LatitudeLongitudeBounds(min, max);
	}

	public double resolution(int zoom) {
		// "Resolution (meters/pixel) for given zoom level (measured at Equator)"

		// s return (2 * math.pi * 6378137) / (self.tileSize * 2**zoom)
		return this.initialResolution / (2 ^ zoom);
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

	public GoogleTilePosition tmsTileToGoogleTile(TmsTilePosition tilePosition, int zoom) {
		// "Converts TMS tile coordinates to Google Tile coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();

		// # coordinate origin is moved from bottom-left to top-left corner of
		// the extent
		return new GoogleTilePosition(tx, (2 ^ zoom - 1) - ty);
	}
	
	public TmsTilePosition googleTileToTmsTile(GoogleTilePosition tilePosition, int zoom) {
		// "Converts Google tile coordinates to  TMS Tile coordinates"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();

		// # coordinate origin is moved from bottom-left to top-left corner of
		// the extent
		return new TmsTilePosition(tx, (2 ^ zoom - 1) - ty);
	}

	public QuadTreeTilePosition tmsTileToQuadTreeTile(TmsTilePosition tilePosition, int zoom) {
		// "Converts TMS tile coordinates to Microsoft QuadTree"
		int tx = tilePosition.getX();
		int ty = tilePosition.getY();

		String quadKey = "";
		ty = (2 ^ zoom - 1) - ty;
		for (int i = zoom; i > 0; i--) {
			int digit = 0;
			int mask = 1 << (i - 1);
			if ((tx & mask) != 0) {
				digit += 1;
			}
			if ((ty & mask) != 0) {
				digit += 2;
			}
			quadKey += digit;

		}
		return new QuadTreeTilePosition(quadKey);
	}
	
}
