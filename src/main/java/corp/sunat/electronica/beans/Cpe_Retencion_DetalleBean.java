package corp.sunat.electronica.beans;

public class Cpe_Retencion_DetalleBean
{
  private int ID_RETENCION_DETALLE;
  private int ID_CABECERA;
  private String COD_TIPO_DOCUMENTO;
  private String NRO_DOCUMENTO;
  private String FECHA_DOCUMENTO;
  private String COD_MONEDA;
  private double MONTO_TOTAL;
  private String FECHA_PAGO;
  private String NRO_DOC_PAGO;
  private String COD_MONEDA_PAGO;
  private double MONTO_PAGO;
  private String FECHA_RETENIDA;
  private String MONEDA_RETENIDA;
  private double MONTO_RETENIDO;
  private String MONEDA_PAGO_NETO;
  private double MONTO_PAGO_NETO;
  private String MONEDA_ORIGEN_TC;
  private String MONEDA_DESTINO_TC;
  private double PORCENTAJE_TC;
  private String FECHA_TC;
  private int TIPO;
  
  public int getID_RETENCION_DETALLE()
  {
    return this.ID_RETENCION_DETALLE;
  }
  
  public void setID_RETENCION_DETALLE(int ID_RETENCION_DETALLE)
  {
    this.ID_RETENCION_DETALLE = ID_RETENCION_DETALLE;
  }
  
  public int getID_CABECERA()
  {
    return this.ID_CABECERA;
  }
  
  public void setID_CABECERA(int ID_CABECERA)
  {
    this.ID_CABECERA = ID_CABECERA;
  }
  
  public String getCOD_TIPO_DOCUMENTO()
  {
    return this.COD_TIPO_DOCUMENTO;
  }
  
  public void setCOD_TIPO_DOCUMENTO(String COD_TIPO_DOCUMENTO)
  {
    this.COD_TIPO_DOCUMENTO = COD_TIPO_DOCUMENTO;
  }
  
  public String getNRO_DOCUMENTO()
  {
    return this.NRO_DOCUMENTO;
  }
  
  public void setNRO_DOCUMENTO(String NRO_DOCUMENTO)
  {
    this.NRO_DOCUMENTO = NRO_DOCUMENTO;
  }
  
  public String getFECHA_DOCUMENTO()
  {
    return this.FECHA_DOCUMENTO;
  }
  
  public void setFECHA_DOCUMENTO(String FECHA_DOCUMENTO)
  {
    this.FECHA_DOCUMENTO = FECHA_DOCUMENTO;
  }
  
  public String getCOD_MONEDA()
  {
    return this.COD_MONEDA;
  }
  
  public void setCOD_MONEDA(String COD_MONEDA)
  {
    this.COD_MONEDA = COD_MONEDA;
  }
  
  public double getMONTO_TOTAL()
  {
    return this.MONTO_TOTAL;
  }
  
  public void setMONTO_TOTAL(double MONTO_TOTAL)
  {
    this.MONTO_TOTAL = MONTO_TOTAL;
  }
  
  public String getFECHA_PAGO()
  {
    return this.FECHA_PAGO;
  }
  
  public void setFECHA_PAGO(String FECHA_PAGO)
  {
    this.FECHA_PAGO = FECHA_PAGO;
  }
  
  public String getNRO_DOC_PAGO()
  {
    return this.NRO_DOC_PAGO;
  }
  
  public void setNRO_DOC_PAGO(String NRO_DOC_PAGO)
  {
    this.NRO_DOC_PAGO = NRO_DOC_PAGO;
  }
  
  public String getCOD_MONEDA_PAGO()
  {
    return this.COD_MONEDA_PAGO;
  }
  
  public void setCOD_MONEDA_PAGO(String COD_MONEDA_PAGO)
  {
    this.COD_MONEDA_PAGO = COD_MONEDA_PAGO;
  }
  
  public double getMONTO_PAGO()
  {
    return this.MONTO_PAGO;
  }
  
  public void setMONTO_PAGO(double MONTO_PAGO)
  {
    this.MONTO_PAGO = MONTO_PAGO;
  }
  
  public String getFECHA_RETENIDA()
  {
    return this.FECHA_RETENIDA;
  }
  
  public void setFECHA_RETENIDA(String FECHA_RETENIDA)
  {
    this.FECHA_RETENIDA = FECHA_RETENIDA;
  }
  
  public String getMONEDA_RETENIDA()
  {
    return this.MONEDA_RETENIDA;
  }
  
  public void setMONEDA_RETENIDA(String MONEDA_RETENIDA)
  {
    this.MONEDA_RETENIDA = MONEDA_RETENIDA;
  }
  
  public double getMONTO_RETENIDO()
  {
    return this.MONTO_RETENIDO;
  }
  
  public void setMONTO_RETENIDO(double MONTO_RETENIDO)
  {
    this.MONTO_RETENIDO = MONTO_RETENIDO;
  }
  
  public String getMONEDA_PAGO_NETO()
  {
    return this.MONEDA_PAGO_NETO;
  }
  
  public void setMONEDA_PAGO_NETO(String MONEDA_PAGO_NETO)
  {
    this.MONEDA_PAGO_NETO = MONEDA_PAGO_NETO;
  }
  
  public double getMONTO_PAGO_NETO()
  {
    return this.MONTO_PAGO_NETO;
  }
  
  public void setMONTO_PAGO_NETO(double MONTO_PAGO_NETO)
  {
    this.MONTO_PAGO_NETO = MONTO_PAGO_NETO;
  }
  
  public String getMONEDA_ORIGEN_TC()
  {
    return this.MONEDA_ORIGEN_TC;
  }
  
  public void setMONEDA_ORIGEN_TC(String MONEDA_ORIGEN_TC)
  {
    this.MONEDA_ORIGEN_TC = MONEDA_ORIGEN_TC;
  }
  
  public String getMONEDA_DESTINO_TC()
  {
    return this.MONEDA_DESTINO_TC;
  }
  
  public void setMONEDA_DESTINO_TC(String MONEDA_DESTINO_TC)
  {
    this.MONEDA_DESTINO_TC = MONEDA_DESTINO_TC;
  }
  
  public double getPORCENTAJE_TC()
  {
    return this.PORCENTAJE_TC;
  }
  
  public void setPORCENTAJE_TC(double PORCENTAJE_TC)
  {
    this.PORCENTAJE_TC = PORCENTAJE_TC;
  }
  
  public String getFECHA_TC()
  {
    return this.FECHA_TC;
  }
  
  public void setFECHA_TC(String FECHA_TC)
  {
    this.FECHA_TC = FECHA_TC;
  }
  
  public int getTIPO()
  {
    return this.TIPO;
  }
  
  public void setTIPO(int TIPO)
  {
    this.TIPO = TIPO;
  }
}
