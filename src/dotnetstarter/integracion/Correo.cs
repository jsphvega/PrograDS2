using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MailKit.Net.Smtp;
using MimeKit;
using MailKit.Security;


namespace dotnethelloworld.integracion
{
    public class Correo

    {
        public void sendEmail(String asunto,String cuerpo,String destinatario)
        {
            var emailMessage = new MimeMessage();
            var message = new MimeMessage();
            message.From.Add(new MailboxAddress("TutorCognitivo", "tutorCognitivo4@gmail.com"));
            message.To.Add(new MailboxAddress(destinatario));
            message.Subject = asunto;
            message.Body = new TextPart("plain")
            {
                Text = cuerpo
            };

            using (var client = new SmtpClient())
            {
                client.Connect("smtp.gmail.com", 587, false);
                client.Authenticate("tutorcognitivo4@gmail.com", "chukyBueno4");
                client.Send(message);
                client.Disconnect(true);
            }



        }

}

}
