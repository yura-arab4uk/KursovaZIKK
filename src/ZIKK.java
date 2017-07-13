public class ZIKK {
	long p = 23071L, q = 11117L, e = 722761L, d = 1L;

	public void primes(long n) {
		int count = 0;
		for (int i = 2; i < n; i++) {
			if (isPrime(i)) {
				count++;
				System.out.print(i + " ");
				if (count % 10 == 0)
					System.out.println();
			}
		}
	}

	boolean isPrime(long n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	long FIn(long p, long q) {
		return (p - 1) * (q - 1);
	}

	// Алгоритм Евкліда. Найбільший спільний дільник
	public long gcd(long a, long b) {
		if (b == 0)
			return a;
		long x = a % b;
		return gcd(b, x);
	}

	boolean rightd() {
		if ((d * e) % FIn(p, q) == 1)
			return true;
		else
			return false;
	}

	long pickUpd() {
		while (d < FIn(p, q)) {
			if (rightd())
				return d;
			else
				d++;
		}
		return -1;
	}

	long toDigit(String Block) {
		long digitM = 0L;
		for (int i = 0; i < Block.length(); i++) {
			if (Block.charAt(i) > 127)
				digitM += Block.charAt(i) - 893;
			else
				digitM += Block.charAt(i);
			if (i != Block.length() - 1)
				digitM = digitM * 1000;
		}
		return digitM;
	}

	long involution(long digitM, long KU[]) {
		long C = digitM;
		for (int i = 1; i < KU[0]; i++) {
			C *= digitM;
			C %= KU[1];
		}
		return C;
	}

	String turnToABlock(String M, int i) {
		int k = 0;
		String Block = "";
		// якщо це останній блок і в ньому менше 3-х символів то
		if ((i / 3 == numberOfBlocks(M) - 1) & (M.length() % 3 != 0)) {
			k = M.length() - i;
		} else
			k = 3;
		for (int j = i; j < i + k; j++) {
			Block += Character.toString(M.charAt(j));
		}
		return Block;
	}

	int numberOfBlocks(String M) {
		int y = M.length() / 3;
		if (M.length() % 3 != 0)
			y++;
		return y;
	}

	String encrypt(String M, long key[]) {
		String Block = "", crypt = "", zeros = "";
		long digitM = 0L, C = 0L;
		for (int i = 0; i < numberOfBlocks(M); i++) {
			zeros = "";
			Block = turnToABlock(M, i * 3);
			digitM = toDigit(Block);
			C = involution(digitM, key);
			for (int v = Long.toString(C).length(); v < 9; v++)
				zeros += "0";
			Block = zeros + Long.toString(C);
			crypt += Block;
		}
		return crypt;
	}

	String decrypt(String crypt, long key[]) {
		String M = "";
		long digitM = 0L, first = 0L, second = 0L, third = 0L;
		for (int i = 0; i < numberOfBlocks(crypt) / 3; i++) {
			digitM = Long.parseLong(crypt.substring(i * 9, i * 9 + 9));
			digitM = involution(digitM, key);
			first = digitM / 1000000;
			second = (digitM - first * 1000000) / 1000;
			third = digitM - first * 1000000 - second * 1000;
			if (first > 127)
				M += Character.toString((char) (first + 893));
			else if (first > 0)
				M += Character.toString((char) (first));
			if (second > 127)
				M += Character.toString((char) (second + 893));
			else if (second > 0)
				M += Character.toString((char) (second));
			if (third > 127)
				M += Character.toString((char) (third + 893));
			else
				M += Character.toString((char) (third));
		}
		return M;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long KU[] = new long[2];
		long KR[] = new long[2];
		ZIKK z = new ZIKK();
		// z.primes(25000);
		// Обираємо прості числа p і q так, що n>M
		// 1.Обчислити особисті приватний та публічний ключі криптосистеми;
		long n = z.p * z.q;
		//

		if (z.gcd(z.e, z.FIn(z.p, z.q)) == 1)
			System.out.println("e підібрано правильно");
		else {
			System.out.println("e підібрано неправильно, програму зупинено");
			System.exit(0);
		}
		z.d = z.pickUpd();
		System.out.println("d=" + (z.d = z.pickUpd()) + "\n");
		//
		KU[0] = z.e;
		KU[1] = n;
		System.out.println("Публікуємо відкритий ключ:\n KU={e,n}={" + KU[0]
				+ "," + KU[1] + "}\n");
		KR[0] = z.d;
		KR[1] = n;
		// 2.Здійснити шифрування довільного повідомлення англійською мовою,
		// якби
		// поданого на вашу адресу з-за кордону Вашим зарубіжним кореспондентом;
		String M = "Hello!Happy New Year!Everything will be fine.";
		System.out.println("Повідомлення зарубіжного кореспондента:\n " + M);
		String crypt = z.encrypt(M, KU);
		System.out.println("Шифрограма цього повідомлення:\n " + crypt);
		// 3.Дешифрувати це повідомлення; переконатися, що розшифрований текст
		// співпадає з початковим;
		String Md = z.decrypt(crypt, KR);
		System.out.println("Розшифровуємо і отримуємо:\n " + Md + "\n");
		// 4.Здійснити опублікування довільного повідомлення українською мовою,
		// яке супроводжується Вашим електронним підписом;
		M = "студент Арабчук Ю. П. група СІм-10-2";
		System.out.println("Публікуємо повідомлення українською мовою:\n " + M);
		crypt = z.encrypt(M, KR);
		System.out.println("Створюємо електронний підпис до нього:\n " + crypt);
		// 5.Показати шляхом розшифровування, що отримана шифрограма дійсно
		// належить особисто Вам;
		Md = z.decrypt(crypt, KU);
		System.out
				.println("Розшифровуємо електронний підпис та ідентифікуємо автора:\n "
						+ Md);
		System.exit(0);
	}
}
