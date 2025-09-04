package com.example.telequiz

// Funciona como una base de datos para las preguntas, de una forma estatica.

object BancoDePreguntas {

    fun obtenerPreguntas(tema: String): List<Pregunta> {
        return when (tema) {
            "Redes" -> obtenerPreguntasDeRedes()
            "Ciberseguridad" -> obtenerPreguntasDeCiberseguridad()
            "Microondas" -> obtenerPreguntasDeMicroondas()
            else -> emptyList()
        }
    }
    // Preguntas relacionadas a redes ( I love OpenStack)
    private fun obtenerPreguntasDeRedes(): List<Pregunta> {
        return listOf(
            Pregunta("¿Qué componente de OpenStack gestiona las máquinas virtuales (cómputo)?", listOf("Cinder", "Neutron", "Nova", "Swift"), 2),
            Pregunta("¿Cuál es el servicio de identidad en OpenStack, encargado de la autenticación y autorización?", listOf("Glance", "Keystone", "Horizon", "Heat"), 1),
            Pregunta("En OpenStack, ¿qué proyecto proporciona el almacenamiento de objetos?", listOf("Swift", "Cinder", "Nova", "Glance"), 0),
            Pregunta("¿Qué tipo de servicio en la nube es OpenStack principalmente?", listOf("SaaS (Software as a Service)", "PaaS (Platform as a Service)", "IaaS (Infrastructure as a Service)", "FaaS (Function as a Service)"), 2),
            Pregunta("El componente de OpenStack responsable de la conectividad de red es:", listOf("Nova", "Quantum", "Swift", "Neutron"), 3),
            Pregunta("¿Qué es 'Glance' en el ecosistema de OpenStack?", listOf("Un panel de control web", "Un servicio de imágenes de disco", "Un motor de orquestación", "Un servicio de telemetría"), 1),
            Pregunta("¿Cuál es una de las principales ventajas de usar OpenStack?", listOf("Es propiedad de una única empresa", "Es de código abierto y evita la dependencia de un proveedor", "Solo funciona en hardware específico", "No requiere configuración de red"), 1)
        )
    }

    // Preguntas relacionadas a Ciberseguridad
    private fun obtenerPreguntasDeCiberseguridad(): List<Pregunta> {
        return listOf(
            Pregunta("¿Cuál es la función principal de un IDS (Sistema de Detección de Intrusos)?", listOf("Bloquear activamente el tráfico malicioso", "Monitorear y alertar sobre actividad sospechosa", "Cifrar los datos de la red", "Autenticar usuarios"), 1),
            Pregunta("¿Qué tipo de IDS compara el tráfico de red con una base de datos de patrones de ataque conocidos?", listOf("IDS basado en anomalías", "IDS basado en firmas", "IDS basado en comportamiento", "IDS basado en estado"), 1),
            Pregunta("Un 'falso positivo' en un IDS se refiere a:", listOf("Un ataque real que no fue detectado", "Una alerta generada por tráfico legítimo", "Un ataque que fue bloqueado exitosamente", "Un error de configuración del sistema"), 1),
            Pregunta("¿Cuál es la principal diferencia entre un IDS y un IPS (Sistema de Prevención de Intrusos)?", listOf("El IDS es más rápido que el IPS", "El IPS puede tomar acciones para detener un ataque, el IDS no", "El IDS solo funciona en la nube", "No hay ninguna diferencia"), 1),
            Pregunta("¿Dónde se coloca un NIDS (Network IDS) para ser más efectivo?", listOf("En cada computadora personal", "En puntos estratégicos de la red para analizar el tráfico", "Dentro del servidor de base de datos", "Únicamente en el router de borde"), 1),
            Pregunta("Un IDS que crea una línea base del comportamiento normal de la red y alerta sobre desviaciones se conoce como:", listOf("Basado en firmas", "Basado en reglas", "Basado en anomalías", "Pasivo"), 2),
            Pregunta("¿Qué significa que un IDS opere en 'modo promiscuo'?", listOf("Que rechaza todos los paquetes", "Que solo inspecciona tráfico cifrado", "Que captura y analiza todos los paquetes que pasan por la red", "Que opera con permisos de administrador"), 2)
        )
    }

    // Preguntas relacionadas a Yarleque xd
    private fun obtenerPreguntasDeMicroondas(): List<Pregunta> {
        return listOf(
            Pregunta("¿Qué describe la 'pérdida de trayectoria en el espacio libre'?", listOf("El aumento de la señal con la distancia", "La atenuación de la señal a medida que se aleja de la fuente", "La reflexión de la señal en la ionosfera", "La velocidad de la onda en el vacío"), 1),
            Pregunta("¿Qué es un radiador isotrópico?", listOf("Una antena que solo recibe señales", "Una antena real con alta ganancia", "Una antena teórica que irradia energía por igual en todas las direcciones", "Un dispositivo que bloquea la radiación"), 2),
            Pregunta("El fenómeno por el cual las ondas se curvan al rodear un obstáculo se llama:", listOf("Reflexión", "Refracción", "Difracción", "Dispersión"), 2),
            Pregunta("La 'polarización' de una onda electromagnética se refiere a:", listOf("La frecuencia de la onda", "La orientación del campo eléctrico", "La velocidad de propagación", "La cantidad de energía que transporta"), 1),
            Pregunta("¿Qué efecto ocurre cuando una señal llega al receptor por múltiples trayectorias?", listOf("Efecto Doppler", "Atenuación", "Propagación multitrayecto (Multipath)", "Ruido térmico"), 2),
            Pregunta("En general, ¿qué relación existe entre la frecuencia de una onda y el tamaño de la antena requerida?", listOf("A mayor frecuencia, mayor tamaño de antena", "A mayor frecuencia, menor tamaño de antena", "El tamaño de la antena no depende de la frecuencia", "Solo las bajas frecuencias requieren antenas"), 1),
            Pregunta("¿Qué es la 'ganancia' de una antena?", listOf("La cantidad de energía que consume", "Su capacidad para enfocar la radiación en una dirección específica", "El área física de la antena", "La resistencia eléctrica de la antena"), 1)
        )
    }
}