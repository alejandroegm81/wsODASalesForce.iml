package FromNET;

public class GlobalQ {

    public String vQueryBSCS = "  select \n" +
            "      row_number() over ( partition by customer_id order by customer_id, ohduedate desc) No,\n" +
            "      datos.* from \n" +
            "      (\n" +
            "      select  \n" +
            "          a.customer_id           , \n" +
            "          e.co_id                 , \n" +
            "          g.dn_num                ,\n" +
            "          concat(concat(c.ccname || ' ',c.ccfname || ' '),c.cclname)  nombre,\n" +
            "          c.passportno              Identificacion,\n" +
            "          nvl(b.prgname,'-') Tipo_Cliente,     \n" +
            "          ccemail                 Correo,\n" +
            "          nvl(sum(case when ohopnamt_doc>0 then ohopnamt_doc end),0)                         Saldo_total,\n" +
            "          nvl(sum(case when ohduedate<trunc(sysdate) then ohopnamt_doc end),0)               Saldo_Vencido,\n" +
            "          nvl(count(*) -1 ,0)                                                                Cantidad_Documentos , \n" +
            "          h.customer_id           id2,\n" +
            "          h.ohxact                docid,\n" +
            "          ''                      doc,\n" +
            "          h.ohrefnum              refe,\n" +
            "          ''                      npe,\n" +
            "          0                      Lectura,\n" +
            "          'FA'                    Factura,\n" +
            "          to_char(h.ohentdate,'dd/MM/yyyy')        Emision,\n" +
            "          to_char(h.ohduedate,'dd/MM/yyyy')        Vencimiento,\n" +
            "          nvl(Round((trunc(sysdate) - h.ohduedate),0),0)  diasMora,\n" +
            "          nvl(case when ohopnamt_doc>0 then ohopnamt_doc end,0)  Saldo,\n" +
            "          nvl(case when ohduedate<trunc(sysdate) then ohopnamt_doc end ,0)SaldoVencido, ohduedate\n" +
            "      from Customer_all@bscs a \n" +
            "              inner join PriceGroup_all@bscs   b on b.prgcode     = a.prgcode \n" +
            "              inner join Ccontact_all@bscs     c on c.customer_id = a.customer_id and c.ccseq       > 0 and c.ccseq  = (select max(x.ccseq) from ccontact_all@bscs x where c.customer_id = x.customer_id and ccbill = 'X') and c.ccbill = 'X'           \n" +
            "              left outer join Trade_all@bscs   d on d.tradecode = a.cstradecode\n" +
            "              inner join  Contract_all@bscs    e on e.customer_id = a.customer_id \n" +
            "              inner join Contr_services_cap@bscs f on  f.co_id    = e.co_id and f.dn_id is not null and f.seqno       = (select max(z.seqno) from contr_services_cap@bscs z where e.co_id = z.co_id and z.seqno > 0) and f.cs_deactiv_date is null     \n" +
            "              inner join directory_number@bscs g on g.dn_id       = f.dn_id  \n" +
            "              left outer join orderhdr_all@bscs h on h.customer_id = a.customer_id and h.ohstatus in ('FS','CS','IN', 'FE') and nvl(h.ohopnamt_doc,1)>0          \n" +
            "      where \t\t\t\t\t      \n" +
            "          {} /*g.dn_num='50378550122'*/\n" +
            "      group by\n" +
            "          a.customer_id           , \n" +
            "          e.co_id                 , \n" +
            "          g.dn_num                ,\n" +
            "          concat(concat(c.ccname || ' ',c.ccfname || ' '),c.cclname)  ,\n" +
            "          c.passportno            ,\n" +
            "          nvl(b.prgname,'-')      ,     \n" +
            "          ccemail                 ,\n" +
            "          h.customer_id           ,\n" +
            "          h.ohxact                ,\n" +
            "          ''                      ,\n" +
            "          h.ohrefnum              ,\n" +
            "          to_char(h.ohentdate,'dd/MM/yyyy')        ,\n" +
            "          to_char(h.ohduedate,'dd/MM/yyyy')        ,\n" +
            "          Round((trunc(sysdate) - h.ohduedate),0)  ,\n" +
            "          case when ohopnamt_doc>0 then ohopnamt_doc end  ,\n" +
            "          case when ohduedate<trunc(sysdate) then ohopnamt_doc end,\n" +
            "          ohduedate\n" +
            "       ) datos \n";

}
