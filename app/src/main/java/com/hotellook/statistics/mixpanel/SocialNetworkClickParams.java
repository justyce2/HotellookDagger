package com.hotellook.statistics.mixpanel;

import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.SingleParams;
import com.hotellook.ui.screen.information.SocialNetworksView;
import pl.charmas.android.reactivelocation.C1822R;

public class SocialNetworkClickParams extends SingleParams {
    public SocialNetworkClickParams(String link) {
        super(MixPanelParams.SOCIAL_NETWORK_NAME, getSocialNetworkName(link));
    }

    private static String getSocialNetworkName(String link) {
        Object obj = -1;
        switch (link.hashCode()) {
            case -1655714898:
                if (link.equals(SocialNetworksView.GOOGLE_PLUS)) {
                    obj = 1;
                    break;
                }
                break;
            case -1359195417:
                if (link.equals(SocialNetworksView.INSTAGRAM)) {
                    obj = 2;
                    break;
                }
                break;
            case -694016594:
                if (link.equals(SocialNetworksView.FACEBOOK)) {
                    obj = null;
                    break;
                }
                break;
            case -478167909:
                if (link.equals(SocialNetworksView.VKONTAKTE)) {
                    obj = 4;
                    break;
                }
                break;
            case 12956007:
                if (link.equals(SocialNetworksView.TWITTER)) {
                    obj = 3;
                    break;
                }
                break;
        }
        switch (obj) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                return MixPanelParams.FACEBOOK;
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                return MixPanelParams.GOOGLE_PLUS;
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                return MixPanelParams.INSTAGRAM;
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                return MixPanelParams.TWITTER;
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                return MixPanelParams.VKONTAKTE;
            default:
                return SortingChangedParams.UNKNOWN;
        }
    }
}
