public class BellmanReturn {
    private String[] stringReturn;
    private double[] doubleReturn;
    public BellmanReturn(String[] s, double[] doubleReturn){
        stringReturn = s;
        this.doubleReturn = doubleReturn;
    }

    public String[] getStringReturn() {
        return stringReturn;
    }

    public void setStringReturn(String[] stringReturn) {
        this.stringReturn = stringReturn;
    }

    public double[] getDoubleReturn() {
        return doubleReturn;
    }

    public void setDoubleReturn(double[] doubleReturn) {
        this.doubleReturn = doubleReturn;
    }
}
