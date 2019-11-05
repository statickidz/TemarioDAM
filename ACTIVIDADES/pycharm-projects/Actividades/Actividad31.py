# -*- coding: utf-8 -*
import re

reExpresion = "abc"
print "Cadena de caracteres abc"
print "Si" if (re.match(reExpresion, "Abc")) else "No"
print "Si" if (re.match(reExpresion, "abc")) else "No"

reExpresion = ".bc"
print "Punto para cualquier carácter .bc"
print "Si" if (re.match(reExpresion, "Abc")) else "No"
print "Si" if (re.match(reExpresion, "abc")) else "No"

reExpresion = "(abc|Abc)"
print "(|) una de las opcines separadas por |"
print "Si" if (re.match(reExpresion, "Abc")) else "No"
print "Si" if (re.match(reExpresion, "abc")) else "No"
print "Si" if (re.match(reExpresion, "cbc")) else "No"

reExpresion = "[0-9]bc"
print "Corchetes más rango [0-9]bc"
print "Si" if (re.match(reExpresion, "Abc")) else "No"
print "Si" if (re.match(reExpresion, "1bc")) else "No"
print "Si" if (re.match(reExpresion, "7bc")) else "No"

reExpresion = "[^0-9]bc"
print "Corchetes más rango negado [^0-9]bc"
print "Si" if (re.match(reExpresion, "Abc")) else "No"
print "Si" if (re.match(reExpresion, "1bc")) else "No"
print "Si" if (re.match(reExpresion, "7bc")) else "No"

reExpresion = "[0-9a-zB]bc"
print "Corchetes más rango concatenado [0-9a-zB]bc"
print "Si" if (re.match(reExpresion, "Abc")) else "No"
print "Si" if (re.match(reExpresion, "Bbc")) else "No"
print "Si" if (re.match(reExpresion, "xbc")) else "No"
print "Si" if (re.match(reExpresion, "7bc")) else "No"

print "    uso de re    "
reExpresion = "http://(.+)(.{2})"  # no es necesario escapar dentro de () el.
print "Si" if (re.match(reExpresion, "HTTP://www.boe.es", re.IGNORECASE | re.VERBOSE)) else "No"
print re.match(reExpresion, "http://www.boe.es").groups()
print re.sub("es", "net", "http://www.boes.es")
