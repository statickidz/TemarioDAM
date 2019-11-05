# -*- encoding: utf-8 -*-


def comprobar_letraDNI(DNI):
    if len(DNI) < 9 or len(DNI) > 9:
        print "Se requieren 8 números y una letra, por favor."
        return False

    lista = DNI[0:8]
    print lista
    if lista.isdigit():
        NIF = 'TRWAGMYFPDXBNJZSQVHLCKE'
        letra = NIF[int(lista) % 23]
        if letra == DNI[8]:
            return True
        else:
            return False
    else:
        print "El DNI introducido contiene caracteres no permitidos."


while True:
    print "Introduzca su DNI: 8 números y letra."
    c = raw_input(">")
    if comprobar_letraDNI(c):
        print "Gracias.El DNI es correcto"
    else:
        print "La letra del DNI no corresponde al número introducido."
