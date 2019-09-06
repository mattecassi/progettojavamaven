create view compitiDaSvolgereQuestaSettimana
as
SELECT *
from compito
where (
    (dow is NULL AND dataCompitoOff >= date("now") and dataCompitoOff <= date("now","+" || (7 - strftime("%w","now")) || " days") )
    or
    (dow is null and dataCompitoOff is NULL)
    or
    (dow = 0 or dow >= strftime("%w", "now"))
  )
  AND NOT EXISTS(
    SELECT *
    FROM compitoSvolto
    where idCompito = compito.ID
      AND
      (

          (
                compito.dow = 0 AND
                dataRisoluzione = date("now","+" || (7 - strftime("%w","now")) || " days")
            )
          or
          (
                compito.dow > 0 AND
                dataRisoluzione = date("now","+" || (compito.dow - strftime("%w","now")) || " days")
            )
          or
          (
              compito.dow is null and compito.dataCompitoOff is not null
            )
          or
          (
              compito.dow is null and compito.dataCompitoOff is null
              and dataRisoluzione = date("now")
            )

        )
  )

