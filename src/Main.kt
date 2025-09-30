import kotlin.math.abs

// Lớp Phân số: tử số (tu), mẫu số (mau)
class PhanSo(private var tu: Int = 1, private var mau: Int = 1) : Comparable<PhanSo> {

    /** Ước chung lớn nhất */
    private fun gcd(a: Int, b: Int): Int {
        var x = abs(a)
        var y = abs(b)
        if (x == 0 && y == 0) return 1
        while (y != 0) {
            val t = x % y
            x = y
            y = t
        }
        return if (x == 0) 1 else x
    }

    /** Chuẩn hoá dấu và rút gọn (đồng thời là phương thức rút gọn) */
    fun rutGon(): PhanSo {
        // Đưa dấu về tử; mẫu luôn dương
        if (mau < 0) {
            tu = -tu
            mau = -mau
        }
        val g = gcd(tu, mau)
        tu /= g
        mau /= g
        return this
    }

    /** In phân số ra màn hình */
    fun xuat() {
        val p = this.copy().rutGon()
        println("${p.tu}/${p.mau}")
    }

    /** So sánh với phân số khác: trả -1, 0, 1 (nhỏ hơn / bằng / lớn hơn) */
    override fun compareTo(other: PhanSo): Int {
        // So sánh bằng tích chéo để tránh sai số
        val left = this.tu.toLong() * other.mau
        val right = other.tu.toLong() * this.mau
        return when {
            left < right -> -1
            left > right -> 1
            else -> 0
        }
    }

    /** Cộng với 1 phân số khác, trả về phân số mới đã rút gọn */
    fun cong(other: PhanSo): PhanSo {
        val tuMoi = this.tu * other.mau + other.tu * this.mau
        val mauMoi = this.mau * other.mau
        return PhanSo(tuMoi, mauMoi).rutGon()
    }

    fun copy(): PhanSo = PhanSo(tu, mau)
    /** Nhập phân số từ bàn phím */
    fun nhapTuBanPhim() {
        while (true) {
            print("  Nhập tử số: ")
            val t = readln().toInt()
            print("  Nhập mẫu số: ")
            val m = readln().toInt()
            if (t == 0 || m == 0) {
                println("  Tử hoặc mẫu đang bằng 0. Vui lòng nhập lại!")
                continue
            }
            tu = t
            mau = m
            rutGon()
            break
        }
    }

    override fun toString(): String = "${copy().rutGon().tu}/${copy().rutGon().mau}"
}

/* ================== CÁC HÀM LÀM VIỆC VỚI MẢNG PHÂN SỐ ================== */

fun nhapMangPhanSo(): MutableList<PhanSo> {
    print("Nhập số lượng phân số n: ")
    val n = readln().toInt()
    val ds = mutableListOf<PhanSo>()
    for (i in 1..n) {
        println("Phân số thứ $i:")
        val p = PhanSo()
        p.nhapTuBanPhim()
        ds.add(p)
    }
    return ds
}

fun inMang(ds: List<PhanSo>, moTa: String) {
    println("\n$moTa")
    ds.forEachIndexed { i, p -> println("[$i] $p") }
}

fun rutGonTatCa(ds: List<PhanSo>): List<PhanSo> = ds.map { it.copy().rutGon() }

fun tongTatCa(ds: List<PhanSo>): PhanSo {
    var s = PhanSo(0, 1)
    ds.forEach { s = s.cong(it) }
    return s.rutGon()
}

fun timMax(ds: List<PhanSo>): PhanSo? = ds.maxOrNull()

fun sapXepGiamDan(ds: MutableList<PhanSo>) {
    ds.sortWith(compareByDescending { it })
}

/* ================== HÀM MAIN CHẠY DEMO THEO ĐÚNG YÊU CẦU ================== */

fun main() {
    //Nhập 1 mảng phân số từ bàn phím
    val ds = nhapMangPhanSo()

    //In ra mảng phân số vừa nhập
    inMang(ds, "Mảng phân số vừa nhập:")

    //Rút gọn các phần tử và in ra kết quả
    val dsRutGon = rutGonTatCa(ds)
    inMang(dsRutGon, "Mảng sau khi rút gọn:")

    //Tính tổng các phân số và in kết quả
    val tong = tongTatCa(dsRutGon)
    println("\nTổng các phân số = $tong")

    //Tìm phân số có giá trị lớn nhất và in
    val maxPs = timMax(dsRutGon)
    println("Phân số lớn nhất = ${maxPs ?: "Không có"}")

    //Sắp xếp mảng theo thứ tự giảm dần và in
    val dsSapXep = dsRutGon.toMutableList()
    sapXepGiamDan(dsSapXep)
    inMang(dsSapXep, "Mảng sắp xếp theo giá trị giảm dần:")
}
