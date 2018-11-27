package pl.wurmonline.deedplanner;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.GoogleAnalyticsBuilder;
import com.brsanthu.googleanalytics.GoogleAnalyticsConfig;
import com.brsanthu.googleanalytics.discovery.AwtRequestParameterDiscoverer;
import com.brsanthu.googleanalytics.request.DefaultRequest;

public final class Analytics {
    
    private static GoogleAnalytics instance;
    
    public static GoogleAnalytics getInstance() {
        if (instance == null) {
            Properties.wake();
            
            GoogleAnalyticsConfig config = new GoogleAnalyticsConfig();
            config.setEnabled(true);
            config.setRequestParameterDiscoverer(new AwtRequestParameterDiscoverer());
            config.setValidate(true);
            
            DefaultRequest defaultRequest = new DefaultRequest();
            defaultRequest.trackingId(Constants.GOOGLE_ANALYTICS_ID);
            defaultRequest.clientId(Properties.uuid);
            defaultRequest.applicationName(Constants.GOOGLE_ANALYTICS_APP_NAME);
            defaultRequest.applicationVersion(Constants.VERSION_STRING);
            
            instance = new GoogleAnalyticsBuilder()
                    .withDefaultRequest(defaultRequest)
                    .withConfig(config)
                    .build();
        }
        
        return instance;
    }
    
}
