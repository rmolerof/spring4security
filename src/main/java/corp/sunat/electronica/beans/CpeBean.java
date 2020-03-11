package corp.sunat.electronica.beans;

public class CpeBean
{
  private int ID;
  private String TIPO_OPERACION;
  private String FECHA_REGISTRO;
  private int ID_EMPRESA;
  private int ID_CLIENTE_CPE;
  private double TOTAL_GRAVADAS;
  private double TOTAL_INAFECTA;
  private double TOTAL_EXONERADAS;
  private double TOTAL_GRATUITAS;
  private double TOTAL_PERCEPCIONES;
  private double TOTAL_RETENCIONES;
  private double TOTAL_DETRACCIONES;
  private double TOTAL_BONIFICACIONES;
  private double TOTAL_DESCUENTO;
  private double SUB_TOTAL;
  private double POR_IGV;
  private double TOTAL_IGV;
  private double TOTAL_ISC;
  private double TOTAL_EXPORTACION;
  private double TOTAL_OTR_IMP;
  private double TOTAL;
  private String TOTAL_LETRAS;
  private String NRO_GUIA_REMISION;
  private String FECHA_GUIA_REMISION;
  private String COD_GUIA_REMISION;
  private String NRO_OTR_COMPROBANTE;
  private String COD_OTR_COMPROBANTE;
  private String TIPO_COMPROBANTE_MODIFICA;
  private String NRO_DOCUMENTO_MODIFICA;
  private String COD_TIPO_MOTIVO;
  private String DESCRIPCION_MOTIVO;
  private String NRO_COMPROBANTE;
  private String FECHA_DOCUMENTO;
  private String FECHA_VTO;
  private String COD_TIPO_DOCUMENTO;
  private String COD_MONEDA;
  private String NRO_DOCUMENTO_CLIENTE;
  private String RAZON_SOCIAL_CLIENTE;
  private String TIPO_DOCUMENTO_CLIENTE;
  private String DIRECCION_CLIENTE;
  private String COD_UBIGEO_CLIENTE;
  private String DEPARTAMENTO_CLIENTE;
  private String PROVINCIA_CLIENTE;
  private String DISTRITO_CLIENTE;
  private String CIUDAD_CLIENTE;
  private String COD_PAIS_CLIENTE;
  private String NRO_DOCUMENTO_EMPRESA;
  private String TIPO_DOCUMENTO_EMPRESA;
  private String NOMBRE_COMERCIAL_EMPRESA;
  private String CODIGO_UBIGEO_EMPRESA;
  private String DIRECCION_EMPRESA;
  private String CONTACTO_EMPRESA;
  private String DEPARTAMENTO_EMPRESA;
  private String PROVINCIA_EMPRESA;
  private String DISTRITO_EMPRESA;
  private String CODIGO_PAIS_EMPRESA;
  private String RAZON_SOCIAL_EMPRESA;
  private String USUARIO_SOL_EMPRESA;
  private String PASS_SOL_EMPRESA;
  private int TIPO_PROCESO;
  private String COD_RESPUESTA_SUNAT;
  private String DESCRIPCION_RESPUESTA;
  private int FLG_ANTICIPO;
  private int FLG_REGU_ANTICIPO;
  private String NRO_COMPROBANTE_REF_ANT;
  private String MONEDA_REGU_ANTICIPO;
  private double MONTO_REGU_ANTICIPO;
  private String TIPO_DOCUMENTO_EMP_REGU_ANT;
  private String NRO_DOCUMENTO_EMP_REGU_ANT;
  private String ESTADO;
  private String HASH_CPE;
  private String HASH_CDR;
  private String CORREO;
  private String PASS_FIRMA;
  private String CONTRA;
  private int TIPO;
  
  public int getID()
  {
    return this.ID;
  }
  
  public void setID(int ID)
  {
    this.ID = ID;
  }
  
  public String getTIPO_OPERACION()
  {
    return this.TIPO_OPERACION;
  }
  
  public void setTIPO_OPERACION(String TIPO_OPERACION)
  {
    this.TIPO_OPERACION = TIPO_OPERACION;
  }
  
  public String getFECHA_REGISTRO()
  {
    return this.FECHA_REGISTRO;
  }
  
  public void setFECHA_REGISTRO(String FECHA_REGISTRO)
  {
    this.FECHA_REGISTRO = FECHA_REGISTRO;
  }
  
  public int getID_EMPRESA()
  {
    return this.ID_EMPRESA;
  }
  
  public void setID_EMPRESA(int ID_EMPRESA)
  {
    this.ID_EMPRESA = ID_EMPRESA;
  }
  
  public int getID_CLIENTE_CPE()
  {
    return this.ID_CLIENTE_CPE;
  }
  
  public void setID_CLIENTE_CPE(int ID_CLIENTE_CPE)
  {
    this.ID_CLIENTE_CPE = ID_CLIENTE_CPE;
  }
  
  public double getTOTAL_GRAVADAS()
  {
    return this.TOTAL_GRAVADAS;
  }
  
  public void setTOTAL_GRAVADAS(double TOTAL_GRAVADAS)
  {
    this.TOTAL_GRAVADAS = TOTAL_GRAVADAS;
  }
  
  public double getTOTAL_INAFECTA()
  {
    return this.TOTAL_INAFECTA;
  }
  
  public void setTOTAL_INAFECTA(double TOTAL_INAFECTA)
  {
    this.TOTAL_INAFECTA = TOTAL_INAFECTA;
  }
  
  public double getTOTAL_EXONERADAS()
  {
    return this.TOTAL_EXONERADAS;
  }
  
  public void setTOTAL_EXONERADAS(double TOTAL_EXONERADAS)
  {
    this.TOTAL_EXONERADAS = TOTAL_EXONERADAS;
  }
  
  public double getTOTAL_GRATUITAS()
  {
    return this.TOTAL_GRATUITAS;
  }
  
  public void setTOTAL_GRATUITAS(double TOTAL_GRATUITAS)
  {
    this.TOTAL_GRATUITAS = TOTAL_GRATUITAS;
  }
  
  public double getTOTAL_PERCEPCIONES()
  {
    return this.TOTAL_PERCEPCIONES;
  }
  
  public void setTOTAL_PERCEPCIONES(double TOTAL_PERCEPCIONES)
  {
    this.TOTAL_PERCEPCIONES = TOTAL_PERCEPCIONES;
  }
  
  public double getTOTAL_RETENCIONES()
  {
    return this.TOTAL_RETENCIONES;
  }
  
  public void setTOTAL_RETENCIONES(double TOTAL_RETENCIONES)
  {
    this.TOTAL_RETENCIONES = TOTAL_RETENCIONES;
  }
  
  public double getTOTAL_DETRACCIONES()
  {
    return this.TOTAL_DETRACCIONES;
  }
  
  public void setTOTAL_DETRACCIONES(double TOTAL_DETRACCIONES)
  {
    this.TOTAL_DETRACCIONES = TOTAL_DETRACCIONES;
  }
  
  public double getTOTAL_BONIFICACIONES()
  {
    return this.TOTAL_BONIFICACIONES;
  }
  
  public void setTOTAL_BONIFICACIONES(double TOTAL_BONIFICACIONES)
  {
    this.TOTAL_BONIFICACIONES = TOTAL_BONIFICACIONES;
  }
  
  public double getTOTAL_DESCUENTO()
  {
    return this.TOTAL_DESCUENTO;
  }
  
  public void setTOTAL_DESCUENTO(double TOTAL_DESCUENTO)
  {
    this.TOTAL_DESCUENTO = TOTAL_DESCUENTO;
  }
  
  public double getSUB_TOTAL()
  {
    return this.SUB_TOTAL;
  }
  
  public void setSUB_TOTAL(double SUB_TOTAL)
  {
    this.SUB_TOTAL = SUB_TOTAL;
  }
  
  public double getPOR_IGV()
  {
    return this.POR_IGV;
  }
  
  public void setPOR_IGV(double POR_IGV)
  {
    this.POR_IGV = POR_IGV;
  }
  
  public double getTOTAL_IGV()
  {
    return this.TOTAL_IGV;
  }
  
  public void setTOTAL_IGV(double TOTAL_IGV)
  {
    this.TOTAL_IGV = TOTAL_IGV;
  }
  
  public double getTOTAL_ISC()
  {
    return this.TOTAL_ISC;
  }
  
  public void setTOTAL_ISC(double TOTAL_ISC)
  {
    this.TOTAL_ISC = TOTAL_ISC;
  }
  
  public double getTOTAL_EXPORTACION()
  {
    return this.TOTAL_EXPORTACION;
  }
  
  public void setTOTAL_EXPORTACION(double TOTAL_EXPORTACION)
  {
    this.TOTAL_EXPORTACION = TOTAL_EXPORTACION;
  }
  
  public double getTOTAL_OTR_IMP()
  {
    return this.TOTAL_OTR_IMP;
  }
  
  public void setTOTAL_OTR_IMP(double TOTAL_OTR_IMP)
  {
    this.TOTAL_OTR_IMP = TOTAL_OTR_IMP;
  }
  
  public double getTOTAL()
  {
    return this.TOTAL;
  }
  
  public void setTOTAL(double TOTAL)
  {
    this.TOTAL = TOTAL;
  }
  
  public String getTOTAL_LETRAS()
  {
    return this.TOTAL_LETRAS;
  }
  
  public void setTOTAL_LETRAS(String TOTAL_LETRAS)
  {
    this.TOTAL_LETRAS = TOTAL_LETRAS;
  }
  
  public String getNRO_GUIA_REMISION()
  {
    return this.NRO_GUIA_REMISION;
  }
  
  public void setNRO_GUIA_REMISION(String NRO_GUIA_REMISION)
  {
    this.NRO_GUIA_REMISION = NRO_GUIA_REMISION;
  }
  
  public String getFECHA_GUIA_REMISION()
  {
    return this.FECHA_GUIA_REMISION;
  }
  
  public void setFECHA_GUIA_REMISION(String FECHA_GUIA_REMISION)
  {
    this.FECHA_GUIA_REMISION = FECHA_GUIA_REMISION;
  }
  
  public String getCOD_GUIA_REMISION()
  {
    return this.COD_GUIA_REMISION;
  }
  
  public void setCOD_GUIA_REMISION(String COD_GUIA_REMISION)
  {
    this.COD_GUIA_REMISION = COD_GUIA_REMISION;
  }
  
  public String getNRO_OTR_COMPROBANTE()
  {
    return this.NRO_OTR_COMPROBANTE;
  }
  
  public void setNRO_OTR_COMPROBANTE(String NRO_OTR_COMPROBANTE)
  {
    this.NRO_OTR_COMPROBANTE = NRO_OTR_COMPROBANTE;
  }
  
  public String getCOD_OTR_COMPROBANTE()
  {
    return this.COD_OTR_COMPROBANTE;
  }
  
  public void setCOD_OTR_COMPROBANTE(String COD_OTR_COMPROBANTE)
  {
    this.COD_OTR_COMPROBANTE = COD_OTR_COMPROBANTE;
  }
  
  public String getTIPO_COMPROBANTE_MODIFICA()
  {
    return this.TIPO_COMPROBANTE_MODIFICA;
  }
  
  public void setTIPO_COMPROBANTE_MODIFICA(String TIPO_COMPROBANTE_MODIFICA)
  {
    this.TIPO_COMPROBANTE_MODIFICA = TIPO_COMPROBANTE_MODIFICA;
  }
  
  public String getNRO_DOCUMENTO_MODIFICA()
  {
    return this.NRO_DOCUMENTO_MODIFICA;
  }
  
  public void setNRO_DOCUMENTO_MODIFICA(String NRO_DOCUMENTO_MODIFICA)
  {
    this.NRO_DOCUMENTO_MODIFICA = NRO_DOCUMENTO_MODIFICA;
  }
  
  public String getCOD_TIPO_MOTIVO()
  {
    return this.COD_TIPO_MOTIVO;
  }
  
  public void setCOD_TIPO_MOTIVO(String COD_TIPO_MOTIVO)
  {
    this.COD_TIPO_MOTIVO = COD_TIPO_MOTIVO;
  }
  
  public String getDESCRIPCION_MOTIVO()
  {
    return this.DESCRIPCION_MOTIVO;
  }
  
  public void setDESCRIPCION_MOTIVO(String DESCRIPCION_MOTIVO)
  {
    this.DESCRIPCION_MOTIVO = DESCRIPCION_MOTIVO;
  }
  
  public String getNRO_COMPROBANTE()
  {
    return this.NRO_COMPROBANTE;
  }
  
  public void setNRO_COMPROBANTE(String NRO_COMPROBANTE)
  {
    this.NRO_COMPROBANTE = NRO_COMPROBANTE;
  }
  
  public String getFECHA_DOCUMENTO()
  {
    return this.FECHA_DOCUMENTO;
  }
  
  public void setFECHA_DOCUMENTO(String FECHA_DOCUMENTO)
  {
    this.FECHA_DOCUMENTO = FECHA_DOCUMENTO;
  }
  
  public String getFECHA_VTO()
  {
    return this.FECHA_VTO;
  }
  
  public void setFECHA_VTO(String FECHA_VTO)
  {
    this.FECHA_VTO = FECHA_VTO;
  }
  
  public String getCOD_TIPO_DOCUMENTO()
  {
    return this.COD_TIPO_DOCUMENTO;
  }
  
  public void setCOD_TIPO_DOCUMENTO(String COD_TIPO_DOCUMENTO)
  {
    this.COD_TIPO_DOCUMENTO = COD_TIPO_DOCUMENTO;
  }
  
  public String getCOD_MONEDA()
  {
    return this.COD_MONEDA;
  }
  
  public void setCOD_MONEDA(String COD_MONEDA)
  {
    this.COD_MONEDA = COD_MONEDA;
  }
  
  public String getNRO_DOCUMENTO_CLIENTE()
  {
    return this.NRO_DOCUMENTO_CLIENTE;
  }
  
  public void setNRO_DOCUMENTO_CLIENTE(String NRO_DOCUMENTO_CLIENTE)
  {
    this.NRO_DOCUMENTO_CLIENTE = NRO_DOCUMENTO_CLIENTE;
  }
  
  public String getRAZON_SOCIAL_CLIENTE()
  {
    return this.RAZON_SOCIAL_CLIENTE;
  }
  
  public void setRAZON_SOCIAL_CLIENTE(String RAZON_SOCIAL_CLIENTE)
  {
    this.RAZON_SOCIAL_CLIENTE = RAZON_SOCIAL_CLIENTE;
  }
  
  public String getCOD_UBIGEO_CLIENTE()
  {
    return this.COD_UBIGEO_CLIENTE;
  }
  
  public void setCOD_UBIGEO_CLIENTE(String COD_UBIGEO_CLIENTE)
  {
    this.COD_UBIGEO_CLIENTE = COD_UBIGEO_CLIENTE;
  }
  
  public String getDEPARTAMENTO_CLIENTE()
  {
    return this.DEPARTAMENTO_CLIENTE;
  }
  
  public void setDEPARTAMENTO_CLIENTE(String DEPARTAMENTO_CLIENTE)
  {
    this.DEPARTAMENTO_CLIENTE = DEPARTAMENTO_CLIENTE;
  }
  
  public String getPROVINCIA_CLIENTE()
  {
    return this.PROVINCIA_CLIENTE;
  }
  
  public void setPROVINCIA_CLIENTE(String PROVINCIA_CLIENTE)
  {
    this.PROVINCIA_CLIENTE = PROVINCIA_CLIENTE;
  }
  
  public String getDISTRITO_CLIENTE()
  {
    return this.DISTRITO_CLIENTE;
  }
  
  public void setDISTRITO_CLIENTE(String DISTRITO_CLIENTE)
  {
    this.DISTRITO_CLIENTE = DISTRITO_CLIENTE;
  }
  
  public String getTIPO_DOCUMENTO_CLIENTE()
  {
    return this.TIPO_DOCUMENTO_CLIENTE;
  }
  
  public void setTIPO_DOCUMENTO_CLIENTE(String TIPO_DOCUMENTO_CLIENTE)
  {
    this.TIPO_DOCUMENTO_CLIENTE = TIPO_DOCUMENTO_CLIENTE;
  }
  
  public String getDIRECCION_CLIENTE()
  {
    return this.DIRECCION_CLIENTE;
  }
  
  public void setDIRECCION_CLIENTE(String DIRECCION_CLIENTE)
  {
    this.DIRECCION_CLIENTE = DIRECCION_CLIENTE;
  }
  
  public String getCIUDAD_CLIENTE()
  {
    return this.CIUDAD_CLIENTE;
  }
  
  public void setCIUDAD_CLIENTE(String CIUDAD_CLIENTE)
  {
    this.CIUDAD_CLIENTE = CIUDAD_CLIENTE;
  }
  
  public String getCOD_PAIS_CLIENTE()
  {
    return this.COD_PAIS_CLIENTE;
  }
  
  public void setCOD_PAIS_CLIENTE(String COD_PAIS_CLIENTE)
  {
    this.COD_PAIS_CLIENTE = COD_PAIS_CLIENTE;
  }
  
  public String getNRO_DOCUMENTO_EMPRESA()
  {
    return this.NRO_DOCUMENTO_EMPRESA;
  }
  
  public void setNRO_DOCUMENTO_EMPRESA(String NRO_DOCUMENTO_EMPRESA)
  {
    this.NRO_DOCUMENTO_EMPRESA = NRO_DOCUMENTO_EMPRESA;
  }
  
  public String getTIPO_DOCUMENTO_EMPRESA()
  {
    return this.TIPO_DOCUMENTO_EMPRESA;
  }
  
  public void setTIPO_DOCUMENTO_EMPRESA(String TIPO_DOCUMENTO_EMPRESA)
  {
    this.TIPO_DOCUMENTO_EMPRESA = TIPO_DOCUMENTO_EMPRESA;
  }
  
  public String getNOMBRE_COMERCIAL_EMPRESA()
  {
    return this.NOMBRE_COMERCIAL_EMPRESA;
  }
  
  public void setNOMBRE_COMERCIAL_EMPRESA(String NOMBRE_COMERCIAL_EMPRESA)
  {
    this.NOMBRE_COMERCIAL_EMPRESA = NOMBRE_COMERCIAL_EMPRESA;
  }
  
  public String getCODIGO_UBIGEO_EMPRESA()
  {
    return this.CODIGO_UBIGEO_EMPRESA;
  }
  
  public void setCODIGO_UBIGEO_EMPRESA(String CODIGO_UBIGEO_EMPRESA)
  {
    this.CODIGO_UBIGEO_EMPRESA = CODIGO_UBIGEO_EMPRESA;
  }
  
  public String getDIRECCION_EMPRESA()
  {
    return this.DIRECCION_EMPRESA;
  }
  
  public void setDIRECCION_EMPRESA(String DIRECCION_EMPRESA)
  {
    this.DIRECCION_EMPRESA = DIRECCION_EMPRESA;
  }
  
  public String getCONTACTO_EMPRESA()
  {
    return this.CONTACTO_EMPRESA;
  }
  
  public void setCONTACTO_EMPRESA(String CONTACTO_EMPRESA)
  {
    this.CONTACTO_EMPRESA = CONTACTO_EMPRESA;
  }
  
  public String getDEPARTAMENTO_EMPRESA()
  {
    return this.DEPARTAMENTO_EMPRESA;
  }
  
  public void setDEPARTAMENTO_EMPRESA(String DEPARTAMENTO_EMPRESA)
  {
    this.DEPARTAMENTO_EMPRESA = DEPARTAMENTO_EMPRESA;
  }
  
  public String getPROVINCIA_EMPRESA()
  {
    return this.PROVINCIA_EMPRESA;
  }
  
  public void setPROVINCIA_EMPRESA(String PROVINCIA_EMPRESA)
  {
    this.PROVINCIA_EMPRESA = PROVINCIA_EMPRESA;
  }
  
  public String getDISTRITO_EMPRESA()
  {
    return this.DISTRITO_EMPRESA;
  }
  
  public void setDISTRITO_EMPRESA(String DISTRITO_EMPRESA)
  {
    this.DISTRITO_EMPRESA = DISTRITO_EMPRESA;
  }
  
  public String getCODIGO_PAIS_EMPRESA()
  {
    return this.CODIGO_PAIS_EMPRESA;
  }
  
  public void setCODIGO_PAIS_EMPRESA(String CODIGO_PAIS_EMPRESA)
  {
    this.CODIGO_PAIS_EMPRESA = CODIGO_PAIS_EMPRESA;
  }
  
  public String getRAZON_SOCIAL_EMPRESA()
  {
    return this.RAZON_SOCIAL_EMPRESA;
  }
  
  public void setRAZON_SOCIAL_EMPRESA(String RAZON_SOCIAL_EMPRESA)
  {
    this.RAZON_SOCIAL_EMPRESA = RAZON_SOCIAL_EMPRESA;
  }
  
  public String getUSUARIO_SOL_EMPRESA()
  {
    return this.USUARIO_SOL_EMPRESA;
  }
  
  public void setUSUARIO_SOL_EMPRESA(String USUARIO_SOL_EMPRESA)
  {
    this.USUARIO_SOL_EMPRESA = USUARIO_SOL_EMPRESA;
  }
  
  public String getPASS_SOL_EMPRESA()
  {
    return this.PASS_SOL_EMPRESA;
  }
  
  public void setPASS_SOL_EMPRESA(String PASS_SOL_EMPRESA)
  {
    this.PASS_SOL_EMPRESA = PASS_SOL_EMPRESA;
  }
  
  public int getTIPO_PROCESO()
  {
    return this.TIPO_PROCESO;
  }
  
  public void setTIPO_PROCESO(int TIPO_PROCESO)
  {
    this.TIPO_PROCESO = TIPO_PROCESO;
  }
  
  public String getCOD_RESPUESTA_SUNAT()
  {
    return this.COD_RESPUESTA_SUNAT;
  }
  
  public void setCOD_RESPUESTA_SUNAT(String COD_RESPUESTA_SUNAT)
  {
    this.COD_RESPUESTA_SUNAT = COD_RESPUESTA_SUNAT;
  }
  
  public String getDESCRIPCION_RESPUESTA()
  {
    return this.DESCRIPCION_RESPUESTA;
  }
  
  public void setDESCRIPCION_RESPUESTA(String DESCRIPCION_RESPUESTA)
  {
    this.DESCRIPCION_RESPUESTA = DESCRIPCION_RESPUESTA;
  }
  
  public int getFLG_ANTICIPO()
  {
    return this.FLG_ANTICIPO;
  }
  
  public void setFLG_ANTICIPO(int FLG_ANTICIPO)
  {
    this.FLG_ANTICIPO = FLG_ANTICIPO;
  }
  
  public int getFLG_REGU_ANTICIPO()
  {
    return this.FLG_REGU_ANTICIPO;
  }
  
  public void setFLG_REGU_ANTICIPO(int FLG_REGU_ANTICIPO)
  {
    this.FLG_REGU_ANTICIPO = FLG_REGU_ANTICIPO;
  }
  
  public String getNRO_COMPROBANTE_REF_ANT()
  {
    return this.NRO_COMPROBANTE_REF_ANT;
  }
  
  public void setNRO_COMPROBANTE_REF_ANT(String NRO_COMPROBANTE_REF_ANT)
  {
    this.NRO_COMPROBANTE_REF_ANT = NRO_COMPROBANTE_REF_ANT;
  }
  
  public String getMONEDA_REGU_ANTICIPO()
  {
    return this.MONEDA_REGU_ANTICIPO;
  }
  
  public void setMONEDA_REGU_ANTICIPO(String MONEDA_REGU_ANTICIPO)
  {
    this.MONEDA_REGU_ANTICIPO = MONEDA_REGU_ANTICIPO;
  }
  
  public double getMONTO_REGU_ANTICIPO()
  {
    return this.MONTO_REGU_ANTICIPO;
  }
  
  public void setMONTO_REGU_ANTICIPO(double MONTO_REGU_ANTICIPO)
  {
    this.MONTO_REGU_ANTICIPO = MONTO_REGU_ANTICIPO;
  }
  
  public String getTIPO_DOCUMENTO_EMP_REGU_ANT()
  {
    return this.TIPO_DOCUMENTO_EMP_REGU_ANT;
  }
  
  public void setTIPO_DOCUMENTO_EMP_REGU_ANT(String TIPO_DOCUMENTO_EMP_REGU_ANT)
  {
    this.TIPO_DOCUMENTO_EMP_REGU_ANT = TIPO_DOCUMENTO_EMP_REGU_ANT;
  }
  
  public String getNRO_DOCUMENTO_EMP_REGU_ANT()
  {
    return this.NRO_DOCUMENTO_EMP_REGU_ANT;
  }
  
  public void setNRO_DOCUMENTO_EMP_REGU_ANT(String NRO_DOCUMENTO_EMP_REGU_ANT)
  {
    this.NRO_DOCUMENTO_EMP_REGU_ANT = NRO_DOCUMENTO_EMP_REGU_ANT;
  }
  
  public String getESTADO()
  {
    return this.ESTADO;
  }
  
  public void setESTADO(String ESTADO)
  {
    this.ESTADO = ESTADO;
  }
  
  public String getHASH_CPE()
  {
    return this.HASH_CPE;
  }
  
  public void setHASH_CPE(String HASH_CPE)
  {
    this.HASH_CPE = HASH_CPE;
  }
  
  public String getHASH_CDR()
  {
    return this.HASH_CDR;
  }
  
  public void setHASH_CDR(String HASH_CDR)
  {
    this.HASH_CDR = HASH_CDR;
  }
  
  public String getCORREO()
  {
    return this.CORREO;
  }
  
  public void setCORREO(String CORREO)
  {
    this.CORREO = CORREO;
  }
  
  public String getPASS_FIRMA()
  {
    return this.PASS_FIRMA;
  }
  
  public void setPASS_FIRMA(String PASS_FIRMA)
  {
    this.PASS_FIRMA = PASS_FIRMA;
  }
  
  public String getCONTRA()
  {
    return this.CONTRA;
  }
  
  public void setCONTRA(String CONTRA)
  {
    this.CONTRA = CONTRA;
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
