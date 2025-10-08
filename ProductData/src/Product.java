public class Product {
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private String statusPremium;
    private String isVerified;

    public Product(String id, String nama, double harga, String kategori, String statusPremium, String isVerified) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.statusPremium = statusPremium;
        this.isVerified = isVerified;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setStatusPremium(String statusPremium) {this.statusPremium = statusPremium; }

    public void setIsVerified(String isVerified) {this.isVerified = isVerified; }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public String getKategori() {
        return this.kategori;
    }

    public  String getStatusPremium() {return statusPremium; }

    public  String getIsVerified() {return isVerified; }
}