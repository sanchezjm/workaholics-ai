# workaholics-ai
Jugando con inteligencia artificial y langchain4j...

Indexando contenido de la mejor web sobre tutoriales de tecnología y programación en castellano www.adictosaltrabajo.com, 
realizamos algunas pruebas de configuración de agentes de ia con el soporte de langchaing4j en una aplicación con Spring Boot 
usando distintos LLMs.

Recomiendo la lectura de los siguientes tutoriales:

* https://www.adictosaltrabajo.com/2024/03/05/langchain4j-potenciando-tus-aplicaciones-java-con-inteligencia-artificial-ia-y-modelos-de-lenguaje-de-gran-escala-llm-primeros-pasos/
* https://www.adictosaltrabajo.com/2024/03/07/langchain4j-spring-boot-ollama-mistral/
* https://www.adictosaltrabajo.com/2024/03/08/langchain4j-rag-retrieval-augmented-generation/
* https://www.adictosaltrabajo.com/2024/03/10/langchain4j-spring-boot-tools-and-chains/

## how to

La aplicación se puede ejecutar son distintos perfiles en función del LLM con que le queramos realizar pruebas:

### openai

Debemos ejecutar la aplicación con el siguiente argumento:
```
-Dspring.profiles.active=open-ai
```

y asegurarnos de configurar una api-key en el fichero application-open-ai.yml

### ollama

Debemos ejecutar la aplicación con el siguiente argumento:
```
-Dspring.profiles.active=ollama
```

y podemos levantar un servidor de ollama local ejecutando el docker-compose que puedes encontrar en `src\test\resources\docker`

El problema es que con la implementación actual de langchain4j para ollama no soporta tools.

### local-ai

Debemos ejecutar la aplicación con el siguiente argumento:

```
-Dspring.profiles.active=local-ai
```

Aún no he completado la configuración de la aplicación para local-ai.

## FAQs

Puedes interactuar por consola y probar a lanzarle preguntas como las siguientes:

```
User: hola, quién eres?
[Generic] Agent: ¡Hola! Soy un asistente virtual aquí para ayudarte. Si tienes alguna pregunta o necesitas información, ¡no dudes en decírmelo!

User: quién puede publicar tutoriales en adictos al trabajo?
[Authors] Agent: En Adictosaltrabajo.com cualquier persona puede aportar conocimiento a la Comunidad tecnológica. Ya somos más de 150 autores compartiendo conocimiento. ¿Te animas?

User: qué pasos hay que dar para participar en adictos al trabajo?
[Authors] Agent: Para participar en Adictosaltrabajo.com y aportar conocimiento a la Comunidad tecnológica, simplemente envía tu tutorial. Cualquier persona puede contribuir, y actualmente somos más de 150 autores compartiendo conocimiento. ¿Te animas a participar? Puedes enviar tu tutorial a través del enlace: Participa <https://www.adictosaltrabajo.com/participa/>. ¡Esperamos tu aportación! © 2023. Adictos al trabajo powered by Autentia.

User: me puedes enumerar los pasos para compartir conocimiento en adictos al trabajo?
[Authors] Agent: Para compartir conocimiento en Adictosaltrabajo.com, sigue estos pasos:

Paso 1: Prepara tu tutorial o contenido educativo.
Paso 2: Selecciona el formato de entrega, como un zip que incluya imágenes y archivos de texto en Markdown, HTML o texto plano.
Paso 3: Envía tu entrada a través del enlace: Participa <https://www.adictosaltrabajo.com/participa/>.

Recuerda que en Adictosaltrabajo.com cualquier persona puede contribuir a la Comunidad tecnológica. ¡Anímate a participar junto a más de 150 autores compartiendo conocimiento!

User: puedo adjuntar enlaces personales a mi ficha de autor si escribo un tutorial en adictos al trabajo?
[Authors] Agent: Sí, puedes adjuntar enlaces personales a tu ficha de autor si escribes un tutorial en Adictosaltrabajo.com. En Adictosaltrabajo.com, cualquier persona puede aportar conocimiento a la Comunidad tecnológica. Si te animas a participar, puedes enviar tu tutorial a través del enlace: Participa <https://www.adictosaltrabajo.com/participa/>. ¡Gracias por compartir conocimiento!

User: y puedo adjuntar una fotografía?
[Authors] Agent: Sí, puedes adjuntar una fotografía a tu entrada de contenido tecnológico. A la hora de escribirla, es recomendable que sea lo más descriptiva posible. Además de la fotografía, puedes incluir código, imágenes o vídeos embebidos. Se requerirá una imagen destacada para el post, una ficha con una breve descripción sobre ti y una fotografía de proporción 1:1’5 (tamaño carné). ¡Gracias por tu interés en compartir contenido tecnológico!

User: qué información tienes sobre diagramas y c4m?
[Generic] Agent: La información sobre diagramas y C4M se encuentra en el siguiente enlace:
- **Título:** Diagramas de arquitectura con C4 model
- **Autor:** Por Jose Manuel Sánchez Suárez
- **Vistas:** 7683
- **Fecha:** 6 de mayo de 2023
- **URL:** [Diagramas de arquitectura con C4 model](https://www.adictosaltrabajo.com/2023/05/06/diagramas-de-arquitectura-con-c4-model/)

```