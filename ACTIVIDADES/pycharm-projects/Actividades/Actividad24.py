# -*-coding: utf-8 -*-
class Mueble:  # el primer parámetro siempre selfen todos
    def __init__(self, tipo):  # Constructor
        self.tipo = tipo  # Propiedad pública

    def getTipo(self):  # método getter
        return self.tipo

    def setTipo(self, tipo):  # método setter
        self.tipo = tipo


class Mesa(Mueble):
    def __init__(self, tipo, color):
        Mueble.__init__(self, tipo)  # Llamamos al padre para inicializar
        self.color = color  # nuestra propiedad

    def getColor(self):  # nuestros métodos
        return self.color

    def setColor(self, color):
        self.color = color


class Mesa2(Mueble):
    def __init__(self, tipo, color):
        Mueble.__init__(self, tipo)
        self.color = color

    def getColor(self):
        return self.color

    def setColor(self, color):
        self.color = color

    def getTipo(self):  # sobrescribimos el padre
        return self.tipo + 1


mu = Mueble(3)  # Llamamos al constructor pero no usamos selfen ninguno al llamar
print"Mueble(3)"
print"El tipo es (método):", mu.getTipo()
print"El tipo es (propiedad):", mu.tipo

ms = Mesa(5, 7)
print"Mesa(5,7)"
print"El tipo es:", ms.getTipo()  # método del padre
print"El color es:", ms.getColor()  # método de la clase hija

ms2 = Mesa2(5, 7)
print"Mesa2(5,7)"
print"El tipo es:", ms2.getTipo()
print"El color es:", ms2.getColor()
