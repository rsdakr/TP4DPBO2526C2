public class Person {
    private String id;
    private String nama;
    private int tahun;
    private String kategori;
    private boolean aktif;

    public Person() {}
    public Person(String id, String nama, int tahun, String kategori, boolean aktif) {
        this.id = id;
        this.nama = nama;
        this.tahun = tahun;
        this.kategori = kategori;
        this.aktif = aktif;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public int getTahun() { return tahun; }
    public String getKategori() { return kategori; }
    public boolean getAktif() { return aktif; }

    public void setId(String id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setTahun(int tahun) { this.tahun = tahun; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }
}
