from Model.Insecto.Antena import Antena
from Model.Insecto.Insecto import Insecto
from Model.Mueble.Mueble import Mueble
from Model.Mueble.Mesa import Mesa

mueble = Mueble("Grande")
mueble.addMedida("435x45").addMedida("534x755").addMedida("8678x34")
print mueble.getTipo()
print mueble.getMedidas()

mesa = Mesa("Peque", "Rojo")
mesa.addMedida(4).addMedida(7).addMedida(8)
print mesa.getTipo()
print mesa.getColor()
print mesa.getMedidas()

insecto = Insecto()
insecto.color = "Verde clarito"
insecto.antenas = Antena("Negro", 1234)

print issubclass(Mesa, Mueble)


