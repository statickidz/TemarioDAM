# -*- coding: utf-8 -*
"""
Actividad 5.38
Escribe un programa que muestre por pantalla los números múltiplos de 7
entre el 1 y el 1000. Utiliza una función a la que se le pase un número y
devuelva True si es múltiplo de 7, o False si no lo es.
"""


def esMultiplo(numero, de):
    if numero % de == 0:
        return True
    else:
        return False

# Mostrar números múltiplos de 7
for n in range(1, 1001):
    if esMultiplo(n, 7):
        print n