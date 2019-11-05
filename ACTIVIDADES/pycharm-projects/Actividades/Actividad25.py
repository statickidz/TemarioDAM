# -*-coding: utf-8 -*-
class UnaPrivada:
    def __init__(self):
        self.__Privada = 1  # Supuesta variable privada
        self.Publica = 2


pr = UnaPrivada()
print"Pública:", pr.Publica
# print"Privada:",pr.__Privada #Lanza una excepción
print"Privada:", pr._UnaPrivada__Privada  # Se puede acceder, se ha renombrado
