# -*- coding: utf-8 -*-

# PROPIEDADES
class Propiedades:
    def __init__(self, dia):
        self.__d = dia  # propiedad__dque almacenará el valor privada

    def __getDia(self):  # llamado al establecer dia, no se puede usar directamente
        return self.__d

    def __setDia(self, dia):  # llamado al recoger dia, no se puede usar directamente
        self.__d = dia
    dia = property(__getDia, __setDia)  # Propiedad diacreada


d = Propiedades(3)
print"Día antes:", d.dia
d.dia = 7
# print"Día después:",d.__getDia() ##Excepción
print"Día después:", d.dia  # nueva propiedad
