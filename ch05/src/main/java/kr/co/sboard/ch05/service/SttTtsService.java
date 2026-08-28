package kr.co.sboard.ch05.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SttTtsService {

    private ChatClient chatClient;
    private OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private OpenAiAudioSpeechModel openAiAudioSpeechModel;

    public SttTtsService(
            ChatClient.Builder chatClientBuilder,
            OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel,
            OpenAiAudioSpeechModel openAiAudioSpeechModel) {

        chatClient = chatClientBuilder.build();
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;

    }

    public String stt(String fname, byte[] bytes) {

        return null;
    }

    public byte[] tts(String text) {

        return null;
    }


}
