package corp.sunat.electronica.beans;

public class Cpe_Rr_DetalleBean
{
  private int ID_CPE_RR_DETALLE;
  private int ID_CABECERA;
  private int ITEM;
  private String COD_TIPO_DOCUMENTO;
  private String SERIE;
  private String NUMERO;
  private String DESCRIPCION;
  private int TIPO;
  
  public int getID_CPE_RR_DETALLE()
  {
    return this.ID_CPE_RR_DETALLE;
  }
  
  public void setID_CPE_RR_DETALLE(int ID_CPE_RR_DETALLE)
  {
    this.ID_CPE_RR_DETALLE = ID_CPE_RR_DETALLE;
  }
  
  public int getID_CABECERA()
  {
    return this.ID_CABECERA;
  }
  
  public void setID_CABECERA(int ID_CABECERA)
  {
    this.ID_CABECERA = ID_CABECERA;
  }
  
  public int getITEM()
  {
    return this.ITEM;
  }
  
  public void setITEM(int ITEM)
  {
    this.ITEM = ITEM;
  }
  
  public String getCOD_TIPO_DOCUMENTO()
  {
    return this.COD_TIPO_DOCUMENTO;
  }
  
  public void setCOD_TIPO_DOCUMENTO(String COD_TIPO_DOCUMENTO)
  {
    this.COD_TIPO_DOCUMENTO = COD_TIPO_DOCUMENTO;
  }
  
  public String getSERIE()
  {
    return this.SERIE;
  }
  
  public void setSERIE(String SERIE)
  {
    this.SERIE = SERIE;
  }
  
  public String getNUMERO()
  {
    return this.NUMERO;
  }
  
  public void setNUMERO(String NUMERO)
  {
    this.NUMERO = NUMERO;
  }
  
  public String getDESCRIPCION()
  {
    return this.DESCRIPCION;
  }
  
  public void setDESCRIPCION(String DESCRIPCION)
  {
    this.DESCRIPCION = DESCRIPCION;
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
