
public class Complex {
	
	//Complex number of the form a+bi
	
	public double re;
	public double im;
	
	private double modulus;
	private double argument;
	
	public Complex(double real, double imaginary) {
		this.re = real;
		this.im = imaginary;
		this.modulus = getModulus();
		this.argument = getArgument();
	}
	
	public static Complex fromPolar(double modulus, double argument) {
		double re = modulus * Math.cos(Math.toRadians(argument));
		double im = modulus * Math.sin(Math.toRadians(argument));
		return new Complex(re, im);
	}
	
	public static Complex fromAngle(double argument) {
		return new Complex(Math.cos(Math.toRadians(argument)), Math.sin(Math.toRadians(argument)));
	}
	
	public double re() {
		return re;
	}
	
	public double im() {
		return im;
	}
	
	public double getModulus() {
		return Math.sqrt(re * re + im * im);	
	}
	
	public double getArgument() {
		return Math.toDegrees(Math.atan2(im, re));
	}
	
	public void rotate(double angle) {
		this.argument = Math.toRadians(this.getArgument() + angle);
		re = this.modulus * Math.cos(this.argument);
		im = this.modulus * Math.sin(this.argument);
	}
	
	public Complex sum(Complex b) {
		double real = re + b.re;
		double imag = im + b.im;
		return new Complex(real, imag);
	}
	
	public Complex times(double scale) {
		return new Complex(re * scale, im * scale);
	}
	
	public Complex times(Complex num) {
		double real = this.re * num.re - this.im * num.im;
		double imag = this.im * num.re + this.re * num.im;
		return new Complex(real, imag);
	}
	
	public Complex conjugate() {
		return new Complex(this.re, this.im);
	}
	
	public Complex dividedBy(Complex num) {
		double factor = 1/(num.re * num.re + num.im * num.im);
		return this.times(factor).times(num.conjugate());
	}
	
	public Complex squared() {
		double real = this.re * this.re - this.im * this.im;
		double imag = 2 * this.re * this.im;
		return new Complex(real, imag);
	}
	
	public Complex pow(double power) {
		double newArg = power * this.argument;
		double newMod = Math.pow(this.modulus, power);
		return fromPolar(newMod, newArg);
	}
	
	public static Complex sum (Complex a, Complex b) {
		return new Complex(a.re + b.re, a.im + b.im);
	}
	
	public void print() {
		long precision = 100000;
		double tRe = (int)(this.re * precision) / precision;
		double tIm = (int)(this.im * precision) / precision;
		double tMod = (int)(this.getModulus() * precision) / precision;
		double tArg = (int)(this.getArgument() * precision) / precision;
		
		System.out.println( tRe + " + " + tIm + "i");
		System.out.println("modulus: " + tMod);
		System.out.println("argument: " + tArg);
	}
}
