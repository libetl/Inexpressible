 <?php

     header("Cache-Control: public");
     header("Content-Description: File Transfer");
     header("Content-Disposition: attachment; filename=inexpressible.jnlp");
     header("Content-Type: application/x-java-jnlp-file");
     header("Content-Transfer-Encoding: binary");

     readfile("inexpressible.jnlp");
?>
