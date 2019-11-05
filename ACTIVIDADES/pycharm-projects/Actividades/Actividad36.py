# -*- coding: utf-8 -*
"""
Actividad 5.36
Escribe un programa que utilice un bucle while para crear una lista con los
números del 1 al 10 y luego la muestre por pantalla. Repítelo con un bucle
for. ¿Cómo lo harías sin usar bucles?
"""

print "Bucle while"
cont = 1
while cont < 10:
    print cont
    cont += 1

print "Bucle for"
for n in range(1, 11):
    print n

print "Otra forma"
print range(1, 11)