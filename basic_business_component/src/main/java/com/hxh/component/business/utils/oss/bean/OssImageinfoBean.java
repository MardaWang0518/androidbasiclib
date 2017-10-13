package com.hxh.component.business.utils.oss.bean;

/**
 * Created by mardawang on 2017/5/31.
 */

public class OssImageinfoBean {


    /**
     * Compression : {"value":"6"}
     * DateTime : {"value":"2015:02:11 15:38:27"}
     * ExifTag : {"value":"2212"}
     * FileSize : {"value":"23471"}
     * Format : {"value":"jpg"}
     * GPSLatitude : {"value":"0deg "}
     * GPSLatitudeRef : {"value":"North"}
     * GPSLongitude : {"value":"0deg "}
     * GPSLongitudeRef : {"value":"East"}
     * GPSMapDatum : {"value":"WGS-84"}
     * GPSTag : {"value":"4292"}
     * GPSVersionID : {"value":"2 2 0 0"}
     * ImageHeight : {"value":"333"}
     * ImageWidth : {"value":"424"}
     * JPEGInterchangeFormat : {"value":"4518"}
     * JPEGInterchangeFormatLength : {"value":"3232"}
     * Orientation : {"value":"7"}
     * ResolutionUnit : {"value":"2"}
     * Software : {"value":"Microsoft Windows Photo Viewer 6.1.7600.16385"}
     * XResolution : {"value":"96/1"}
     * YResolution : {"value":"96/1"}
     */

    private CompressionBean Compression;
    private DateTimeBean DateTime;
    private ExifTagBean ExifTag;
    private FileSizeBean FileSize;
    private FormatBean Format;
    private GPSLatitudeBean GPSLatitude;
    private GPSLatitudeRefBean GPSLatitudeRef;
    private GPSLongitudeBean GPSLongitude;
    private GPSLongitudeRefBean GPSLongitudeRef;
    private GPSMapDatumBean GPSMapDatum;
    private GPSTagBean GPSTag;
    private GPSVersionIDBean GPSVersionID;
    private ImageHeightBean ImageHeight;
    private ImageWidthBean ImageWidth;
    private JPEGInterchangeFormatBean JPEGInterchangeFormat;
    private JPEGInterchangeFormatLengthBean JPEGInterchangeFormatLength;
    private OrientationBean Orientation;
    private ResolutionUnitBean ResolutionUnit;
    private SoftwareBean Software;
    private XResolutionBean XResolution;
    private YResolutionBean YResolution;

    public CompressionBean getCompression() {
        return Compression;
    }

    public void setCompression(CompressionBean Compression) {
        this.Compression = Compression;
    }

    public DateTimeBean getDateTime() {
        return DateTime;
    }

    public void setDateTime(DateTimeBean DateTime) {
        this.DateTime = DateTime;
    }

    public ExifTagBean getExifTag() {
        return ExifTag;
    }

    public void setExifTag(ExifTagBean ExifTag) {
        this.ExifTag = ExifTag;
    }

    public FileSizeBean getFileSize() {
        return FileSize;
    }

    public void setFileSize(FileSizeBean FileSize) {
        this.FileSize = FileSize;
    }

    public FormatBean getFormat() {
        return Format;
    }

    public void setFormat(FormatBean Format) {
        this.Format = Format;
    }

    public GPSLatitudeBean getGPSLatitude() {
        return GPSLatitude;
    }

    public void setGPSLatitude(GPSLatitudeBean GPSLatitude) {
        this.GPSLatitude = GPSLatitude;
    }

    public GPSLatitudeRefBean getGPSLatitudeRef() {
        return GPSLatitudeRef;
    }

    public void setGPSLatitudeRef(GPSLatitudeRefBean GPSLatitudeRef) {
        this.GPSLatitudeRef = GPSLatitudeRef;
    }

    public GPSLongitudeBean getGPSLongitude() {
        return GPSLongitude;
    }

    public void setGPSLongitude(GPSLongitudeBean GPSLongitude) {
        this.GPSLongitude = GPSLongitude;
    }

    public GPSLongitudeRefBean getGPSLongitudeRef() {
        return GPSLongitudeRef;
    }

    public void setGPSLongitudeRef(GPSLongitudeRefBean GPSLongitudeRef) {
        this.GPSLongitudeRef = GPSLongitudeRef;
    }

    public GPSMapDatumBean getGPSMapDatum() {
        return GPSMapDatum;
    }

    public void setGPSMapDatum(GPSMapDatumBean GPSMapDatum) {
        this.GPSMapDatum = GPSMapDatum;
    }

    public GPSTagBean getGPSTag() {
        return GPSTag;
    }

    public void setGPSTag(GPSTagBean GPSTag) {
        this.GPSTag = GPSTag;
    }

    public GPSVersionIDBean getGPSVersionID() {
        return GPSVersionID;
    }

    public void setGPSVersionID(GPSVersionIDBean GPSVersionID) {
        this.GPSVersionID = GPSVersionID;
    }

    public ImageHeightBean getImageHeight() {
        return ImageHeight;
    }

    public void setImageHeight(ImageHeightBean ImageHeight) {
        this.ImageHeight = ImageHeight;
    }

    public ImageWidthBean getImageWidth() {
        return ImageWidth;
    }

    public void setImageWidth(ImageWidthBean ImageWidth) {
        this.ImageWidth = ImageWidth;
    }

    public JPEGInterchangeFormatBean getJPEGInterchangeFormat() {
        return JPEGInterchangeFormat;
    }

    public void setJPEGInterchangeFormat(JPEGInterchangeFormatBean JPEGInterchangeFormat) {
        this.JPEGInterchangeFormat = JPEGInterchangeFormat;
    }

    public JPEGInterchangeFormatLengthBean getJPEGInterchangeFormatLength() {
        return JPEGInterchangeFormatLength;
    }

    public void setJPEGInterchangeFormatLength(JPEGInterchangeFormatLengthBean JPEGInterchangeFormatLength) {
        this.JPEGInterchangeFormatLength = JPEGInterchangeFormatLength;
    }

    public OrientationBean getOrientation() {
        return Orientation;
    }

    public void setOrientation(OrientationBean Orientation) {
        this.Orientation = Orientation;
    }

    public ResolutionUnitBean getResolutionUnit() {
        return ResolutionUnit;
    }

    public void setResolutionUnit(ResolutionUnitBean ResolutionUnit) {
        this.ResolutionUnit = ResolutionUnit;
    }

    public SoftwareBean getSoftware() {
        return Software;
    }

    public void setSoftware(SoftwareBean Software) {
        this.Software = Software;
    }

    public XResolutionBean getXResolution() {
        return XResolution;
    }

    public void setXResolution(XResolutionBean XResolution) {
        this.XResolution = XResolution;
    }

    public YResolutionBean getYResolution() {
        return YResolution;
    }

    public void setYResolution(YResolutionBean YResolution) {
        this.YResolution = YResolution;
    }

    public static class CompressionBean {
        /**
         * value : 6
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class DateTimeBean {
        /**
         * value : 2015:02:11 15:38:27
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ExifTagBean {
        /**
         * value : 2212
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class FileSizeBean {
        /**
         * value : 23471
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class FormatBean {
        /**
         * value : jpg
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class GPSLatitudeBean {
        /**
         * value : 0deg
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class GPSLatitudeRefBean {
        /**
         * value : North
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class GPSLongitudeBean {
        /**
         * value : 0deg
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class GPSLongitudeRefBean {
        /**
         * value : East
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class GPSMapDatumBean {
        /**
         * value : WGS-84
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class GPSTagBean {
        /**
         * value : 4292
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class GPSVersionIDBean {
        /**
         * value : 2 2 0 0
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ImageHeightBean {
        @Override
        public String toString() {
            return "ImageHeightBean{" +
                    "value='" + value + '\'' +
                    '}';
        }

        /**
         * value : 333
         */

        private String value;

        public int getValue() {
            return Integer.parseInt(value);
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ImageWidthBean {

        @Override
        public String toString() {
            return "ImageWidthBean{" +
                    "value='" + value + '\'' +
                    '}';
        }

        /**
         * value : 424
         */

        private String value;

        public int getValue() {
            return Integer.parseInt(value);
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class JPEGInterchangeFormatBean {
        /**
         * value : 4518
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class JPEGInterchangeFormatLengthBean {
        /**
         * value : 3232
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class OrientationBean {
        /**
         * value : 7
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ResolutionUnitBean {
        /**
         * value : 2
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SoftwareBean {
        /**
         * value : Microsoft Windows Photo Viewer 6.1.7600.16385
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class XResolutionBean {
        /**
         * value : 96/1
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class YResolutionBean {
        /**
         * value : 96/1
         */

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "OssImageinfoBean{" +
                "Compression=" + Compression +
                ", DateTime=" + DateTime +
                ", ExifTag=" + ExifTag +
                ", FileSize=" + FileSize +
                ", Format=" + Format +
                ", GPSLatitude=" + GPSLatitude +
                ", GPSLatitudeRef=" + GPSLatitudeRef +
                ", GPSLongitude=" + GPSLongitude +
                ", GPSLongitudeRef=" + GPSLongitudeRef +
                ", GPSMapDatum=" + GPSMapDatum +
                ", GPSTag=" + GPSTag +
                ", GPSVersionID=" + GPSVersionID +
                ", ImageHeight=" + ImageHeight +
                ", ImageWidth=" + ImageWidth +
                ", JPEGInterchangeFormat=" + JPEGInterchangeFormat +
                ", JPEGInterchangeFormatLength=" + JPEGInterchangeFormatLength +
                ", Orientation=" + Orientation +
                ", ResolutionUnit=" + ResolutionUnit +
                ", Software=" + Software +
                ", XResolution=" + XResolution +
                ", YResolution=" + YResolution +
                '}';
    }
}
