# -*- coding: utf-8 -*-
try:
    f = open("mi.file", "a+")
except Exception, ex:
    exit

f.write("hola\r")
f.close()

f = open("mi.file", "r")
while True:
    print "Posición:", f.tell()
    I = f.readline()
    if not I: break
    print I,

print "Esta cerrado?", f.closed
f.close()
print "Esta cerrado?", f.closed

# otra forma no es necesario cerrar
with open("mi.file") as archivo:
    for linea in archivo:
        print linea
